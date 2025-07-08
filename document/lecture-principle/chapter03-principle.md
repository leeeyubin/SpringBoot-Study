# 스프링 핵심 원리 이해 1

## 비즈니스 요구사항 설계
- 지금까지 서버를 개발하면서 느낀 점은 "`비즈니스 요구사항을 잘 작성해야 한다`"는 것이다.
- ERD 등을 설계할 때도 이러한 점이 크게 작용했었다. 따라서 다음의 비즈니스 요구사항을 자세히 살펴보도록 하자.

```
1. 회원
- 회원을 가입하고 조회할 수 있다.
- 회원은 일반과 VIP 두 가지 등급이 있다.
- 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다. (미확정)

2. 주문과 할인 정책
- 회원은 상품을 주문할 수 있다.
- 회원 등급에 따라 할인 정책을 적용할 수 있다.
- 할인 정책은 모든 VIP에게 1,000원을 할인해주는 고정 금액 할인으로 적용해달라. (나중에 변경될 수 있다.)
- 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루고 싶다. 최악의 경우 할인 자체를 적용하지 않을 수 있다. (미확정)
```

## 회원 도메인 설계
- 첫 번째 비즈니스 로직인 `회원 도메인`을 개발해 볼 것이다.
- 우선 비즈니스 요구사항을 정리하면 아래와 같다.

<img width="759" alt="image" src="https://github.com/user-attachments/assets/71ea8533-8e35-458f-9329-1560ba812756" />
<br>
<img width="770" alt="image" src="https://github.com/user-attachments/assets/e750e694-bf80-436f-bf67-371610c69bcb" />

## 회원 도메인 개발
- 회원은 일반과 VIP 두 등급이 있으므로 enum class를 활용해 아래처럼 나타내 준다.
```java
public enum Grade {
    BASIC,
    VIP
}
```
- 회원 엔티티는 다음과 같다. 
- ⭐️ `control` + `enter`: 생성자, getter, setter를 쉽게 만들 수 있는 단축키
```java
public class Member {

    private Long id;
    private String name;
    private Grade graade;

    public Member(Long id, String name, Grade graade) {
        this.id = id;
        this.name = name;
        this.graade = graade;
    }

    // getter, setter 생략 ...
}
```

- 이제 회원을 저장할 저장소를 구현해 보자.
- 회원을 가입하고 조회할 수 있는 기능이 있어야 하기 때문에 `save`와 `findById` 함수를 작성해 준다.
```java
public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
```
- 회원 저장소 구현체는 다음과 같다.
```java
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
```

- 마지막으로 서비스를 작성해 주면 된다.
- 똑같은 흐름으로 `interface`와 `구현체`를 각각 작성해 준다.
```java
public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
}
```
```java
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
```

- 실제로 실행해 본 테스트 코드도 정상적으로 동작하는 것을 확인하였다.

<img width="480" alt="image" src="https://github.com/user-attachments/assets/26eefa30-58bb-42db-8403-47c30ce7df20" />

## 주문과 할인 도메인 설계
- 두 번째 비즈니스 로직인 `주문과 할인 도메인`을 개발해 볼 것이다.
- 비즈니스 요구사항을 정리하면 아래와 같다.

<img width="781" alt="image" src="https://github.com/user-attachments/assets/d9beeaae-1c21-4045-9e82-e864e8273e37" />
<br>
<img width="612" alt="image" src="https://github.com/user-attachments/assets/8e253acf-fba2-42f9-84d3-82f0336066a7" />
<br>
<img width="616" alt="image" src="https://github.com/user-attachments/assets/cb40d141-f3b8-4806-b1e0-b01be0ae2d9c" />

## 주문과 할인 도메인 개발
- 할인 정책을 위한 `interface`와 `구현체`를 작성해 준다.
- "`모든 VIP에게 1,000원을 할인해주는 고정 금액 할인으로 적용해달라`"는 요구사항이 있었으므로 if 조건문을 통해 분기처리 한다.
```java

public interface DiscountPolicy {
    /**
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);
}
```
```java
public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000; // 1000원 할인

    @Override
    public int discount(Member member, int price) {
        if (member.getGraade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
```

- 그다음, 주문 엔티티를 작성해 준다.
- ⭐️ `control` + `enter`: toStirng() 쉽게 만들 수 있는 단축키
```java
public class Order {

    private Long memberId;
    private String itemName;
    private int itemPrice;
    private int discountPrice;

    public Order(Long memberId, String itemName, int itemPrice, int discountPrice) {
        this.memberId = memberId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
    }

    public int calculatePrice() {
        return itemPrice - discountPrice;
    }

    // getter, setter 생략 ...

    @Override
    public String toString() {
        return "Order{" +
                "memberId=" + memberId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", discountPrice=" + discountPrice +
                '}';
    }
}

```

- 마지막으로 주문 서비스의 interface와 구현체는 다음과 같다.
```java
public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
```
```java
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
```

- 테스트 코드도 정상적으로 동작하는 것을 확인함으로써 예제 만드는 것을 마무리 하겠다.

<img width="487" alt="image" src="https://github.com/user-attachments/assets/6cf3e3f8-21db-4b6c-bedf-aa3fbfc6cef5" />
