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

## 회원 웹 기능 - 등록

- 회원 등록이 가능한 폼을 만들어 준다.
- `createForm()` 함수는 "/members/new"경로로 HTTP GET 요청이 들어왔을 때 호출됨을 의미한다.
- 이때 "members/createMemberForm"을 반환하고 있는데, 이는 html 파일이다.

```java
@Controller
public class MemberController {

    private final MemberService memberService;
    
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }
}
```

<img width="387" alt="image" src="https://github.com/user-attachments/assets/79f18162-5e4d-467a-b6ea-5c4763cca7d9" />


### 회원등록 컨트롤러

- 웹 등록 화면에서 데이터를 전달 받을 폼 객체를 작성해 준다.
```java
public class MemberForm {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

- 그러면 실제로 등록해 주는 함수는 아래와 같다.
- "/members/new"로 경로는 동일하지만 POST로 되어있기 때문에 해당 함수가 호출되는 것이다.
- `MemberForm`에 클라이언트가 보내준 데이터를 받고, `Member` 객체를 생성해 이름을 저장해 준다.
- "redirect:/"는 루트 경로로 리다이렉트 한다는 의미이다.
```java
@PostMapping("/members/new")
public String create(MemberForm form) {
    Member member = new Member();
    member.setName(form.getName());

    memberService.join(member);

    return "redirect:/";
}
```

## 회원 웹 기능 - 조회
- 회원 컨트롤러에서 조회 기능을 작성한다.

```java
@GetMapping("/members")
public String list(Model model) {
    List<Member> members = memberService.findMembers();
    model.addAttribute("members", members);
    return "members/memberList";
}
```

<img width="408" src="https://github.com/user-attachments/assets/a8b0a9dd-09ec-45e9-932b-7fe941979838" />
