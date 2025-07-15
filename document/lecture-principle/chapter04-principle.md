# 스프링 핵심 원리 이해2

## 새로운 할인 정책 개발
>`요청`: 고정 금액 할인이 아니라, 정률% 할인으로 바꿔주세요.

- 따라서 `RateDiscountPolicy` 추가

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

- 문제점인 이유
  - 추상 인터페이스뿐만 아니라 구현 클래스에도 의존을 하고 있다!
  - 추상 인터페이스: `DiscountPolicy`
  - 구현 클래스: `FixDiscountPolicy`, `RateDiscountPolicy`

<img width="598" height="263" src="https://github.com/user-attachments/assets/afd01e7a-b00b-492a-8af3-1636c9df6927" />

