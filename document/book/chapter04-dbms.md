# DMBS

## DBMS의 개요

### RDBMS
> RDBMS는, 관계형 데이터베이스를 관리하는 시스템이다.

- 일대일 관계: 한 튜플이 다른 테이블의 한 튜플과 연결된 관계
- 일대다 관계: 한 튜플이 다른 테이블의 여러 튜플과 연결된 관계
- 다대다 관계: 여러 튜플이 다른 테이블의 여러 튜플과 연결된 관계

#### 📍 SQL

- `DDL`: 데이터 정의어 ( CREATE, DROP, ALTER, TRUNCATE )
- `DML`: 데이터 조작어 ( SELECT, INSERT, UPDATE, DELETE )
- `DCL`: 데이터 제어어 ( GRANT, REVOKE )

#### 📍 RDBMS의 종류

- Oracle(오라클), MySQL(마이에스큐엘), PostgreSQL(포스트그레에스큐엘)

<img width="100" src="https://github.com/user-attachments/assets/320cb01e-5022-4e39-a7b0-07cd52931ca3" />

<img width="100" src="https://github.com/user-attachments/assets/6204d943-981b-4b63-85cd-c89334492b53" />

<img width="100" src="https://github.com/user-attachments/assets/c6c57af2-8fb8-4e33-b445-41191d2dd2ff" />

### NoSQL
> NoSQL은 SQL을 사용하지 않는 DBMS이다.

- 데이터 저장 유형이 다양하며, 대표적으로 도큐먼트 데이터베이스, 키-값 데이터베이스, 그래프 데이터베이스가 있다.

#### 📍 도큐먼트 데이터베이스

- `JSON`: 'JavaScript Object Notation'의 약자로, 네트워크 통신망에서 데이터 저장 및 전송시에 사용하는 데이터 교환 형식이다.
- `XML`: 'eXtensible Markup Language'의 약자로, HTML처럼 태그 형식으로 데이터를 교환한다.

```json
{
  "name" : "Bona",
  "age" : 23,
  "city" : "Seoul"
}
```

```xml
<data>
    <name>Bona</name>
    <age>23</age>
    <city>Souel</city>
</data>
```

- 도큐먼트 데이터베이스를 저장하는 대표적인 프로그램은 `MongoDB(몽고디비)`이다.
- MongoDB는 데이터를 JSON 도큐먼트 형태로 저장한다.

```json
[
  {
    "name" : "Bona",
    "age" : 23,
    "city" : "Seoul"
  },
  {
    "name" : "Mana",
    "age" : 24,
    "city" : "Busan"
  }
]
```

## RDBMS에서의 CRUD

### 테이블 만들기


## NoSQL에서의 CRUD