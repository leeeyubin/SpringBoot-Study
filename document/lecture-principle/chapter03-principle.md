# 스프링 핵심 원리 이해 1

## 비즈니스 요구사항 설계
- 코드를 작성하기 위해서는 비즈니스 요구사항 설계를 잘 작성하는 것이 중요하다고 생각한다.
- 그래서 먼저 짚고 넘어가보자.

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
- 첫 번째 비즈니스 로직인 회원 도메인을 개발해 보자.
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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Grade getGraade() {
        return graade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGraade(Grade graade) {
        this.graade = graade;
    }
}
```

- 이제 회원을 저장할 저장소를 구현해 보자.
- 회원을 가입하고 조회할 수 있는 기능이 있어야 하기 때문에 `save와` `findById` 함수를 작성해 준다.
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
- 똑같은 흐름으로 `interface`와 구현체를 각각 작성해 준다.
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
- 두 번째 비즈니스 로직인 주문과 할인 도메인을 개발해 보자.