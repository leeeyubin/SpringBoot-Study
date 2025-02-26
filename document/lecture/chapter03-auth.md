# 회원관리 예제

## 비즈니스 요구사항 정리
> 이번 공부에서는 서버에서 기본적으로 비즈니스 요구사항을 어떻게 처리하는 지에 대해 공부했다. <br>
> 아직 데이터 저장소가 선정되지 않은 가상의 시나리오이다. <br>
> _(클라이언트 개발과 비슷한 부분이 많아 신기했다.)_

- 데이터: 회원ID, 이름
- 기능: 회원 등록, 조회

#### 클래스 의존 관계
<img width="692" alt="image" src="https://github.com/user-attachments/assets/fdaa440b-5781-4f1c-be17-3c8ff2954b4b" />

### 회원 도메인과 레포지토리 만들기

- `domain`에 회원 객체를 만들어 준다.
```java
public class Member {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

- `interface`를 사용해야 구현체가 수정되었을 때 관리하기 용이하다.
- `Optional<T>`은 Java 8부터 제공되는 제네릭 클래스로, null이 될 수 있는 값을 감싸서 보다 명시적으로 다룰 수 있다.
```java
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}

```
- 회원 레포지토리 구현체는 아래와 같다.
- 회원의 ID와 이름을 `HashMap`을 이용하여 저장하고 있다.
  - 실무에서는 `ConcurrentHashMap`을 사용해 스레드 안전을 확보한다고 한다.
- 여기서도 `Optional<T>`이 활용된 걸 볼 수 있는데, ID값이 null일 수 있기 때문에 ofNullable로 감싼 것이다.
  - 즉, null일 경우 `Optional.empty()`가 반환되는 것이다.
```java
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
```

### 회원 레포지토리 테스트 케이스 작성

- 코드로 작성한 것은 `테스트코드`를 작성하여 확인할 수 있다.
- `JUnit`이라는 프레임워크로 테스트를 실행할 수 있다.
- 회원 레포지토리 테스트 코드는 아래와 같다.
  - `@AfterEach`에 주목해보면, 이는 각 테스트가 끝날 때마다 실행되는 함수이다. clearStore() 함수를 통해 메모리 DB에 저장된 정보를 지우고 있다. 
  - 즉, 독립적으로 테스트가 가능해진 것이다.

```java
class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        Assertions.assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        Assertions.assertThat(result).hasSize(2);
    }
}
```

### 회원 서비스 개발

- 아래 코드는 회원 서비스 역할을 하는 코드이다.

```java
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     *  전체  회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
```
### 회원 서비스 테스트

- 💡이번 공부에서 어쩌면 가장 익숙한 개념이기도 했던 `DI`에 대해 집중해보자.
- 원래 테스트를 작성할 때 `회원 서비스`가 `회원 레포지토리`를 직접 생성하게 했다.
  - 근데 이렇게 하게 되면, `회원 레포지토리`가 수정되었을 때 `회원 서비스`까지 수정을 해야 한다는 불편함이 생기게 된다.
  - 테스트 코드를 작성할 때에도 `회원 서비스` 테스트 클래스 안에 `회원 레포지토리` 객체를 별도로 생성하면 실제 `회원 레포지토`리의 객체와는 다른 인스턴스이기 때문에 테스트 결과의 신뢰도가 떨어지게 된다.
  - 그래서 외부에서 의존 객체를 생성하도록 수정한 것이다. 
```java
public class MemberService {

    private final MemberRepository memberRepository = new MemberRepository();
}
```

- 최종 테스트 코드는 아래와 같다.
- `@BeforeEach`에 대해서도 주목해보면 좋을 것 같다. 이는 각 테스트 실행 전에 호출되는 기능을 하며, 아래 코드에서는 각 새로운 객체를 생성해주는 역할을 한다.
```java
public class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
    }
}
```