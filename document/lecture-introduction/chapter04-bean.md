# 스프링 빈과 의존관계

## 컴포넌트 스캔과 자동 의존관계 설정

- `MemberController`가 `MemberService`와 `MemberRepository`를 사용하기 위해서는 의존관계를 설정해줘야 한다.

```java
@Controller
public class MemberController {

    private final MemberService memberService;
    
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
```
- 위 코드처럼 생성자에 `@Authwired` 어노테이션이 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어준다. 
  - 이것이 바로 [저번 시간](https://github.com/leeeyubin/SpringBoot-Study/blob/master/document/lecture/chapter03-auth.md#%ED%9A%8C%EC%9B%90-%EC%84%9C%EB%B9%84%EC%8A%A4-%ED%85%8C%EC%8A%A4%ED%8A%B8)에도 알아보았던 DI(의존성 주입)이다.

```
Parameter 0 of constructor in com.springboot.study.controller.MemberController required a bean of type 'com.springboot.study.service.MemberService' that could not be found.
```
- 이렇게만 작성한다면 위와 같은 오류가 발생할 것이다.
  - 바로, `memberService`가 스프링 빈으로 등록되어 있지 않기 때문에 발생하였다.
    <img width="749" alt="image" src="https://github.com/user-attachments/assets/3d12f4b8-7dfa-4d96-bce1-ca654813c9b7" />

- 그렇다면, 스프링 빈을 등록하는 방법은 2 가지이다.
  1) 컴포넌트 스캔과 자동 의존 관계 설정
  2) 자바 코드로 직접 스프링 빈 등록하기

### 1️⃣ 컴포넌트 스캔 원리

- `@Component` 어노테이션이 있으면 스프링 빈이 자동으로 등록된다.
  - ➡️`@Controller`, `@Service`, `@Repository`

```java
@Service // 이 어노테이션으로 스프링 빈 등록!
public class MemberService {

    private final MemberRepository memberRepository;
    
    @Authwired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
```
```java
@Repository // 이 어노테이션으로 스프링 빈 등록!
public class MemoryMemberRepository implements MemberRepository { }
```
- 위 코드로 인해 만들어진 의존관계는 아래와 같다.
<img width="748" alt="image" src="https://github.com/user-attachments/assets/c8128032-727f-43f2-82ec-c7395b7449d6" />

### 2️⃣ 자바 코드로 직접 스프링 빈 등록하기

```java
@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
```
- 지금까지 작성했던 어노테이션은 지워주고, 위와 같이 `SpringConfig`파일을 만들어 주면 된다!

### ⭐ 참고사항
- `의존성 주입`에는 다음 3가지 방법이 있다. 
- 그러나, 의존관계가 실행 중에 동적으로 변하는 경우는 거의 없으므로 `생성자 주입`을 권장한다고 한다.

1) 필드 주입
```java
@Controller
public class MemberController {
   @Autowired private MemberService memberService;
}
```
2. 생성자 주입
```java
@Controller
public class MemberController {
  private final MemberService memberService;
  
  @Autowired
  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }
}
```
3. setter 주입
```java
@Controller
public class MemberController {

  private MemberService memberService;

   @Autowired
   public void setMemberService(MemberService memberService) {
       this.memberService = memberService;
   }
}
```