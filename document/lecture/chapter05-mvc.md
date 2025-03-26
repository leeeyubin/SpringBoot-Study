# 회원관리 예제 - 웹 MVC 개발

## 회원 웹 기능 - 홈 화면 추가
- 홈에 대한 컨트롤러를 추가해 준다.
```java
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
```
- 그리고 홈 화면 html을 만들어준다.

<img width="356" alt="image" src="https://github.com/user-attachments/assets/48a1c6c0-1293-42e4-9384-f01589ace69b" />

