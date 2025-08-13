# 스프링 컨테이너와 스프링 빈

## 스프링 컨테이너 생성 과정
- 스프링 컨테이너가 생성되는 과정은 다음과 같다.

```java
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
```

<img  height="300" src="https://github.com/user-attachments/assets/6562229c-6530-4c42-b815-042ab3baffbf" />

- 위처럼 생성된 스프링 컨테이너는 파라미터로 넘어온 설정 클래스 정보를 사용해서 스프링 빈을 등록한다.
- 이때 빈 이름은 보통 메서드를 사용하는데, 직접 부여할 수도 있다.

## 컨테이너에 등록된 모든 빈 조회

- 아래 코드를 통해 실제 스프링 빈이 잘 등록되어 있는지 확인할 수 있다.

```java
public class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name  = " + beanDefinitionName + ", object = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name  = " + beanDefinitionName + ", object = " + bean);
            }
        }
    }
}
```

## 스프링 빈 조회 - 기본

- 스프링 컨테이너에서 스프링 빈을 찾는 가장 기본적인 조회 방법은 다음과 같다.
  - `ac.getBean(빈이름, 타입)`
  - `ac.getBean(타입)`
- 만약, 조회 대상이 없으면 예외를 발생시킨다.
```java
public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2() {
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByNameX() {
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxx", MemberService.class));
    }
}
```

## 스프링 빈 조회 - 동일한 타입이 둘 이상