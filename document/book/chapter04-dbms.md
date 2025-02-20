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
    <city>Seoul</city>
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

- 테이블을 만들 때는 SQL의 `CREATE`문을 다음과 같은 형식으로 사용한다.

```sql
CREATE TABLE 테이블명 (
    속성명1, 데이터_타입,
    속성명2, 데이터_타입,
    PRIMARY KEY (속성명) /* 기본키 선언 */
);
```

- 만약 특정 속성의 데이터 타입으로 `NULL`을 허용하지 않는 경우에는 "데이터_타입" 다음에 `NOT NULL`을 붙인다.

### 데이터 CRUD

#### 📍 데이터 생성하기

- 테이블에 데이터를 생성(삽입)할 때는 다음과 같은 형식으로 `INSERT` 문을 사용한다.

```sql
INSERT INTO 테이블명 (속성명1, 속성명2, 속성명3)
VALUES (속성값1, 속성값2, 속성값3);
```

#### 📍 데이터 조회하기

- 테이블의 데이터를 조회할 때는 `SELECT` 문을 사용한다.

```sql
SELECT 속성명1, 속성명2
FROM 테이블명
WHERE 조건;
```

#### 📍 데이터 수정하기

- 테이블의 데이터를 수정할 때는 다음과 같은 형식으로 `UPDATE` 문을 사용한다.

```sql
UPDATE 테이블명
SET 속성명 = 변경할_값
WHERE 조건;
```
#### 📍 데이터 삭제하기

- 테이블의 데이터를 삭제할 때는 다음과 같은 형식으로 `DELETE` 문을 사용한다.
```sql
DELETE FROM 테이블명
WHERE 조건;
```

### 테이블 조인

> 조인(join)은 2개 이상의 테이블을 연결해 관련 데이터를 함께 검색하는 데 사용하는 문법으로, 테이블들의 공통 속성 값을 기준으로 테이블끼리 연결한다.

#### 📍 INNER JOIN

- `INNER JOIN`은 두 테이블에서 공통된 속성 값을 가지고 있는 튜플을 반환한다.

#### 📍FULL OUTER JOIN

- `FULL OUTER JOIN`은 왼쪽 테이블과 오른쪽 테이블의 모든 행을 반환한다.

#### 📍 LEFT JOIN

- `LEFT JOIN`은 기준 테이블인 왼쪽 테이블의 모든 튜플을 결과에 포함하고, 오른쪽 테이블에서는 왼쪽 테이블과 일치하는 값을 가진 튜플만 결과에 포함한다.

#### 📍 RIGHT JOIN

- `RIGHT JOIN`은 기준 테이블인 오른쪽 테이블의 모든 튜플을 결과에 포함하고, 왼쪽 테이블에서는 오른쪽 테이블과 일치하는 값을 가진 튜플만 결과에 포함한다.

## NoSQL에서의 CRUD

### MongoDB의 특징

- MongoDB는 컬렉션(collection)과 도큐먼트(document)를 사용해 데이터를 저장한다.

### 컬렉션 만들기

- 'company'라는 데이터베이스에서 작업한다고 가정하고 MongoDB 명령을 셸에서 작성해보자.
```
company > 
```
- 데이터베이스에 컬렉션을 만들 때는 다음 명령어를 사용한다.
```
db.createCollection("컬렉션명")
```

### 데이터 CRUD

#### 📍데이터 생성하기

- 도큐먼트에 데이터를 생성하는 명령은 다음과 같다.

```
db.컬렉션명.insert({field: value, field: value})
```

#### 📍데이터 조회하기

- 데이터를 조회할 때 컬렉션의 모든 도큐먼트를 조회할 수도 있고, 특정 조건에 부합하는 도큐먼트만 조회할 수도 있다.

```
db.컬렉션명.find() // 모든 도큐먼트 조회
db.컬렉션명.find({조건}) // 조건에 부합하는 도큐먼트 조회
```
#### 📍데이터 수정하기

- `updateOne`: 조건에 부합하는 도큐먼트 중 맨 처음 도큐먼트만 수정한다.
- `updateMany`: 조건에 부합하는 모든 도큐먼트를 수정한다.

```
db.컬렉션명.updateOne({filter}, {update})
db.컬렉션명.updateMany({filter}, {update})
```
#### 📍데이터 삭제하기

- `deleteOne`: 조건에 부합하는 도큐먼트 중 맨 처음 도큐먼트만 삭제한다.
- `deleteMany`: 조건에 부합하는 모든 도큐먼트를 삭제한다.

```
db.컬렉션명.deleteOne({조건})
db.컬렉션명.deleteMany({조건})
db.컬렉션명.deleteMany({})
```
