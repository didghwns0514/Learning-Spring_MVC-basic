<link href="../../githubCSS/style.css" rel="stylesheet">

# API 방식

- 탬플릿 엔진 rendering 방식과 대비
- 텍스트 형식 및 JSON 등을 받는 식으로 동작

## 1) 예시

- Example

  - JAVA

    ```JAVA

      @GetMapping(value = "hello-string")
      @ResponseBody // 응답 Body 부분에 직접 response 를 넣어서 돌려준다는 의미
      public String helloString(@RequestParam(name = "name", required = false) String name) {
          return "Hello! This is response from String : " + name; // 이 문자가 그대로 나감
      }
    ```

  - Result - Terminal

      <img src='images/2021-10-06-21-13-00.png' />
