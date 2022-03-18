# 거래내역 관리 API 서버

### 요구사항
- 입출금 거래 데이터 파일(CSV)을 데이터베이스에 저장
- '유저'별로 특정 일자에 발생한 입출금 내역을 조회
- '은행'별로 특정 일자에 발생한 입출금 내역을 조회

### CSV 규격
| 거래 ID(id) | 연도(year) | 월(month) | 일(day) | 유저ID(user_id) | 은행코드(bank_code) | 거래액(transaction_amount) | 거래타입(transaction_type) |
| --- | --- | --- | --- | --- | --- | --- | --- |


## ERD

### 도메인 설계
![image](https://user-images.githubusercontent.com/41888956/155889406-3b34f0ec-1676-4dad-97dd-5aeaa5dec197.png)


### 데이터베이스 구조
![image](https://user-images.githubusercontent.com/41888956/155889238-ce1cfe42-784f-469f-8607-ccc0aebff14b.png)

### 엔티티 구조
![image](https://user-images.githubusercontent.com/41888956/155889247-3610b7b2-a4e0-4930-a447-fa70ebf1c87f.png)


## 문제 해결 전략

### 기술 스택

- Spring Boot 2.6.3
- JPA - hibernate
- MySQL 8
- opencsv

### API 별 플로우 순서
#### 1. CSV 파일 DB 저장 자동화

- [Controller] POST API로 CSV 파일을 입력 받는다.
- [Service] 임시 공간에 저장한다.
- [Service] [CSV 파일 행 규격 객체](https://github.com/lauvsong/transaction-manager/blob/master/src/main/java/com/lauvsong/carrot/domain/CsvRow.java)로 변환한다.
- [Service] [거래내역 엔티티](https://github.com/lauvsong/transaction-manager/blob/master/src/main/java/com/lauvsong/carrot/domain/Transaction.java)로 변환한다.
- [Service] 포함된 `user_id` 모두 [사용자 엔티티](https://github.com/lauvsong/transaction-manager/blob/master/src/main/java/com/lauvsong/carrot/domain/User.java)로 변환한다.
- [Repository] 사용자 엔티티들을 DB에 저장한다.
- [Repository] 거래내역을 DB에 저장한다. (단, batch size인 10,000 단위로 bulk insert 한다.)
- [Service] CSV 파일을 삭제한다.

#### 2. 유저 별 거래내역 조회

- [Controller] GET API 쿼리스트링을 통해 파라미터를 입력 받는다.
- [Contrller] 파라미터를 `Optional` 객체로 변환한다.
- [Service] [Repository](https://github.com/lauvsong/transaction-manager/blob/master/src/main/java/com/lauvsong/carrot/repository/UserRepository.java)에 파라미터를 전달한다.
- [Repository] `fetch join`을 통해 조건에 맞는 결과를 `List<User>`로 반환한다.
- [Service] DTO 변환 후 반환한다.

#### 3. 은행 별 거래내역 조회

- [Controller] GET API 쿼리스트링을 통해 파라미터를 입력 받는다.
- [Contrller] 파라미터를 `Optional` 객체로 변환한다.
- [Service] [Repository](https://github.com/lauvsong/transaction-manager/blob/master/src/main/java/com/lauvsong/carrot/repository/BankRepository.java)에 파라미터를 전달한다.
- [Repository] `fetch join`을 통해 조건에 맞는 결과를 `List<Bank>`로 반환한다.
- [Service] DTO 변환 후 반환한다.

## 서버 구동법

1. 소스코드 다운로드

```shell
git clone https://github.com/lauvsong/transaction-manager
```

2. 데이터베이스 실행

```shell
cd ${PROJECT_DIR}/db
docker-compose up -d
```

3. 서버 빌드

```shell
cd ${PROJECT_DIR}
gradlew build
```

4. 서버 실행

```shell
cd ${PROJECT_DIR}/build/libs
java -jar carrot-0.0.1-SNAPSHOT.jar
```
