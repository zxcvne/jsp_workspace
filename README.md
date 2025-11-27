화면 <-> 서버 <-> DB
servlet(jsp) => spring => springBoot => 대규모
react + next.js => 소규모 (앱 + 웹)

- 게시판 CRUD(게시글 + 댓글 + 검색 + 페이징 + 파일업로드) + 로그인(ADMIN)

servlet(jsp) => 화면(jsp) 서버(servlet) DB(mybatis)
=> Dynamic Web Project
=> jsp_project / dynamic web module 4.0
servlect version 4.0 + jdk 11 + Tomcat 9.0.x
spring => 화면(jsp) 서버(spring) DB(mybatis) 빌드툴(maven)
springBoot => 화면(thymeleaf) 서버(springboot) DB(jpa) 빌드툴(gradle)

---

window-> preferences

1. jdk11 / compiler 11
2. encoding => web java 전부 utf-8
3. web browser => external Chrome
4. server 등록
5. console size => 1000000 정도로 늘리기

프로젝트 우클릭 => properties

1. java build path > jdk-11 / compiler
2. project Facets => 4.0 11 => 변경 => runtime 설정

하단 서버 올리기
port => 8080(인터넷도 8080을 쓰는곳이 많아 충동이 자주 발생함.)
8088 / 8089 / 8090 변경 가능
path = / 로 변경

---

mvnRepository 사이트에서 라이브러리 다운로드
jstl-1.2
mysql-connector-j 8.0.33
mybatis 3.5.10
log4j-api 2.18.0
log4j-core 2.18.0
log4j-slf4j-impl 2.18.0
log4j-web 2.18.0
slf4j-api 1.7.36

---

라이브러리 설정

1. web.xml
2. log4j.xml
3. mybatis.mxl

---

xml 설정

1. log4j2 : 구글 검색(log4j2) -> https://logging.apache.org => MANUAL => Config
2. mybatis : https://mybatis.org/mybatis-3/ko/index.html

---

작업 패키지 구성
jsp <-> controller <-> service <-> dao <-> DB

java : 서버(DB)

- controller : servlet class
- service : impl / interface
- repository (DAO) : impl / interface
- orm (db설정) : 연결 class / xml
- mapper : xml
- domain (VO객체) : class

webapp : 화면 구성 파일(jsp, css, js, image, font)

- resources : css, image...
- board => jsp 파일
- user

게시판 + 댓글 + 파일 + 로그인

---

---

게시판 DB 설정

## mybatisConfig.xml

---

VO : Value Object(주로 DB 테이블의 객체로 사용되는 값을 정의)
DTO : Data Transfer Object (데이터 전송 객체) // `VO 묶음`
DAO : Data Access Obejct (DB에 직접 접근하는 객체)
ORM : Object-Relational Mapping (객체와 테이블간의 관계를 맵핑)

---

일반 파일변경은 tomcat이 적용해주지만
xml라인에서 변경사항은 서버를 끄고 다시 시작해야함.

---

게시판 + 댓글 + 파일 + 로그인
board CRUD + 댓글 + 페이징(검색) + 파일업로드 + 로그인 CRUD

board CRUD (페이징, 검색, 파일업로드, 로그인) - 동기방식
댓글 - 비동기방식 => js =>
JSON 라이브러리 => json-simple-1.1.1
