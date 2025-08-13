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
