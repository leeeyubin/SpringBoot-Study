# 스프링 핵심 원리 이해2

## 새로운 할인 정책 개발
>`요청`: 고정 금액 할인이 아니라, 정률% 할인으로 바꿔주세요.

- 따라서 `RateDiscountPolicy`를 추가해 준다.

<img width="628" height="305" src="https://github.com/user-attachments/assets/39614f8c-a775-4c61-8804-a8e65ad7ed6c" />

```java
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGraade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
```
## 새로운 할인 정책 적용과 문제점
- 할인 정책을 변경하려면 클라이언트인 `OrderServiceImpl`을 고쳐야 한다.
```java
public class OrderServiceImpl implements OrderService{
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
}
```

- ✅문제점인 이유
  - 추상 인터페이스뿐만 아니라 구현 클래스에도 의존을 하고 있다!
  - 추상 인터페이스: `DiscountPolicy`
  - 구현 클래스: `FixDiscountPolicy`, `RateDiscountPolicy`

<img width="598" height="263" src="https://github.com/user-attachments/assets/afd01e7a-b00b-492a-8af3-1636c9df6927" />

- ✅해결 방법
  - DIP를 위반하지 않도록 인터페이스에만 의존하도록 의존관계를 변경한다!
```java
public class OrderServiceImpl implements OrderService{
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private DiscountPolicy discountPolicy;
}
```

- ✅의문점
  - 구현체가 없기 때문에 이렇게만 작성할 경우 NPE이 발생할 텐데 괜찮을까?
- ✅해결방법
  - 클라이언트인 `OrderServiceImpl`에 `DiscountPolicy` 구현 객체를 대신 생성하고 주입해 주면 된다.

## 관심사의 분리
- `AppConfig`
  - 애플리케이션의 전체 동작 방식을 구성(config)하기 위해 구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스를 만들어 준다.
```java
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }

}
```

- 이때 각 클래스에 생성자를 작성해주어야 컴파일 오류가 발생하지 않는다.

<img width="625" height="384" src="https://github.com/user-attachments/assets/bd850f11-ca3e-4c6d-ab46-bd301e2d5f40" />

- 이같은 작업을 통해 객체를 생성하고 연결하는 역할과 생성하는 역할이 명확히 분리가 된 것을 확인할 수 있다.

## AppConfig 리팩토링

- 기존 `AppConfig` 파에서 중복되는 부분을 제거하고, 역할에 따른 구현이 보이도록 바꾸면 다음과 같다.

```java
public class AppConfig {
  public MemberService memberService() {
    return new MemberServiceImpl(memberRepository());
  }

  private MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }

  public OrderService orderService() {
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  public DiscountPolicy discountPolicy() {
    return new FixDiscountPolicy();
  }
}
```

## 새로운 구조와 할인 정책 적용

- 정액 할인 정책을 정률% 할인 정책으로 변경이 된다고 가정하자.
  - 즉, `FixDiscountPolicy`를 `RateDiscountPolicy`로 바꾸면 된다.

<img width="700" src="https://github.com/user-attachments/assets/de2fa5ae-b557-45a3-b853-1e935a239ab8" />

- 그럼 바꿔야 될 부분은 `AppConfig` 파일이 있는 구성 영역이 될 것이다.