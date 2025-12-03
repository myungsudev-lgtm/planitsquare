# Planitsquare – Public Holiday API Service

Planitsquare Subject Public Github Repository

1. 빌드 & 실행 방법

Gradle 빌드 명령어 : ./gradlew clean build

애플리케이션 실행 명령어 : java -jar build/libs/holidayCountry.jar

Swagger 접속 URL : http://localhost:8080/swagger-ui/index.html

※ 참고 OpenAPI : http://localhost:8080/v3/api-docs

2. REST API 요약 명세
   2.1 공휴일 검색
   GET /api/holiday/search

파라미터 타입 설명
year Integer 조회 연도
country String 국가 코드
type String 공휴일 타입 필터
from/to LocalDate 기간 검색
page / size Integer 페이징 옵션(default size : 50)

응답 예시
url : http://localhost:8080/api/holiday?year=2024&country=KR&page=0&size=20&sort=string

response : {
"content": [
{
"date": "2024-01-01",
"localName": "새해",
"name": "New Year's Day",
"country": "KR",
"fixed": false,
"global": true,
"counties": null,
"launchYear": null,
"types": [
"Public"
]
},
{
"date": "2024-02-09",
"localName": "설날",
"name": "Lunar New Year",
"country": "KR",
"fixed": false,
"global": true,
"counties": null,
"launchYear": null,
"types": [
"Public"
]
},...
]
}

2.2 공휴일 재동기화 (Refresh)
POST /api/holiday/refresh

파라미터 타입 설명
year Integer 조회 연도
country String 국가 코드

url : http://localhost:8080/api/holiday/refresh?year=2022&country=KR

response : [
{
"date": "2022-01-01",
"localName": "새해",
"name": "New Year's Day",
"country": "KR",
"fixed": false,
"global": true,
"counties": null,
"launchYear": null,
"types": [
"Public"
]
},
{
"date": "2022-01-31",
"localName": "설날",
"name": "Lunar New Year",
"country": "KR",
"fixed": false,
"global": true,
"counties": null,
"launchYear": null,
"types": [
"Public"
]
},...
]

2.3 공휴일 삭제
DELETE /api/holiday

파라미터 타입 설명
year Integer 삭제 대상 연도
country String 국가 코드

url : http://localhost:8080/api/holiday?year=2022&country=KR

response: status code 204

2.4 국가 조회
GET /api/country

3. ./gradlew clean test 성공 스크린샷
   ./gradlew clean test
   ![alt text](image.png)

4. Swagger UI 또는 OpenAPI JSON 확인 방법

Swagger 접속 경로 : http://localhost:8080/swagger-ui/index.html
OpenAPI JSON 노출 URL : http://localhost:8080/v3/api-docs

---

5. 프로젝트 개요 (상세)
   목적
   국가별 공휴일 수집/저장/조회 서비스 구축
   데이터 중복 방지 및 재동기화 기능 제공
   REST API 기반의 확장 가능한 구조

핵심 기능
최근 5년(2020~2025) 공휴일 데이터 일괄 적재
다중 필터 기반의 공휴일 검색
연도·국가 조합의 재동기화(Refresh)
연도·국가 공휴일 삭제

6. 기술 스택
   항목 내용
   Framework Spring Boot 3.2.5
   ORM JPA, Hibernate, QueryDSL
   DB H2
   API Docs Springdoc OpenAPI (Swagger UI)
   외부 API Nager.Date Public Holiday API
