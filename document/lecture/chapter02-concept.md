# 스프링 웹 개발 기초

## 1. 정적 컨텐츠
<img width="662" src="https://github.com/user-attachments/assets/33dd6553-8ea2-4828-8e47-c26a613d5d74" />

#### 📍 동작 방식
- 웹 브라우저가 `localhost:8080/hello-static.html` 이라고 요청
- 스프링 컨테이너는 `hello-static`을 찾으려 했지만 관련 컨트롤러가 없음
- 정적인 `hello-static.html` 파일이 있다면 이를 반환해줌

## 2. MVC와 테플릿 엔진
<img width="818" src="https://github.com/user-attachments/assets/73f9319d-35a2-402e-a744-3f5c8b2fb65c" />

#### 📍MVC

- ***M (Model)*** : 어플리케이션에 대한 정보가 담김
- ***V (View)*** : 사용자에게 보여지는 화면
- ***C (Controller)*** : 데이터와 비즈니스 로직 사이 상호작용을 담당

#### 📍 동작 방식

```java
@GetMapping("hello-mvc")
public String helloMvc(@RequestParam("name") String name, Model model){
    model.addAttribute("name", name);
    return "hello-template";
}
```
- 웹 브라우저가 ```localhost:8080/hello-mvc?name=spring!!!!```를 요청
    - 참고로, ```@RequestParam```이 있으면 name에 값을 넣어줘야 함
- 스프링 컨테이너에 ```HelloController```가 존재함을 확인
- ```name = spring```으로 들어간 모델에 담겨 ```viewResolver로``` 넘김
- ```viewResolver가``` ```hello-template.html```을 찾아서 반환해줌


=> 결과

<img width="400" src="https://github.com/user-attachments/assets/0a24ac5b-9842-41f7-aee5-7f2523a725ef" />

## 3. API
<img width="794" src="https://github.com/user-attachments/assets/0bd7cfe6-33de-488d-9835-19d1a911fcbb" />

#### 📍동작 방식

```java
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
```

- 웹 브라우저가 ``localhost:8080/hello-api?name=spring!!!`` 요청
- 이때 `@ResponseBody`를 사용하면 `viewResolver`를 사용하지 않음
- 대신 HTTP의 BODY에 문자 내용을 직접 반환함
- `HttpMessageConverter` 동작
  - 단순 문자면 StringConverter가 동작
  - json이면 JsonConverter가 동작
- 그리고 그 객체를 웹으로 반환함

=> 결과

<img width="400" src="https://github.com/user-attachments/assets/579cfd01-8b12-49ed-9d0b-d5e0bd304b34" />
