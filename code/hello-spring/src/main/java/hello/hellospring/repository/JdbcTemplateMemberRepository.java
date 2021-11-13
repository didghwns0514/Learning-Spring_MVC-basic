package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.*;

@Component
public class JdbcTemplateMemberRepository implements MemberRepository{

    // 생성자 주입이 권장되는 방법
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        // SQL 자동 생성 : jdbcInsert 활용; 자동으로 Query 생성, document에 잘 설명되어있음 + 필요하면 찾아서 사용
        // member: Column 명, usingGeneratedKeyColumns : PK관련 생성(이전 값에서 +1 되어서 계속 생성 - h2 데이터베이스 pk설정과 맞춤)
        // 다음 SQL을 대신함 : INSERT INTO <table name>(parm1, parm2, ...) VALUES(var1, var2, ...);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("name", member.getName());
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue()); // pk값 member에 추가해줌

        return member;
    }

    @Override
    public Optional<Member> findByID(Long id) {

        // List<Member> 리턴하도록 되어있음
        // JdbcMemberRepository : 되게 코드가 김, template method 라는 방법을 활용
        ArrayList<Member> memberArrayList = (ArrayList<Member>) jdbcTemplate.query("select * from member where id = ?",
                memberRowMapper(), id);

        return memberArrayList.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public ArrayList<Member> findAll() {
        return (ArrayList<Member>) jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
