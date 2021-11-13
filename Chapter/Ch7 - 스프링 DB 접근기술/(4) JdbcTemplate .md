<link href="../../githubCSS/style.css" rel="stylesheet">

# Jdbc Template

- 이 부분은, JDBC 연결을 변경하기 위함
- Jdcb API에서의 반복 코드(Connection 추가하고 다시 없애는 등의 반복작업)
- 단, SQL문은 직접 작성을 해주어야 함
- **`JdbcTemplate 는 실무에서도 자주 사용`**

## 1) 구현

- 원리 : TemplateMethod, Callback을 사용하여 자동으로 db transaction 서비스 하위에서 처리
- Test :
  - **`MemberRepositoryIntegrationTest 로 수행하면, DB 가져오고 할 필요가 없이, Transaction 단위로 잘 테스트 할 수 있음`**
- Example

  - JAVA

    ```JAVA

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
              ArrayList<Member> memberArrayList = (ArrayList<Member>) jdbcTemplate.query("select * from member where id = ?", memberRowMapper());

              return memberArrayList.stream().findAny();
          }

          @Override
          public Optional<Member> findByName(String name) {
              return Optional.empty();
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

    ```
