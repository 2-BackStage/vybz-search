# VYBZ Search Service

VYBZ 플랫폼의 검색 기능을 담당하는 마이크로서비스입니다.

## 📋 목차

-   [개요](#개요)
-   [기술 스택](#기술-스택)
-   [주요 기능](#주요-기능)
-   [프로젝트 구조](#프로젝트-구조)
-   [API 문서](#api-문서)
-   [설치 및 실행](#설치-및-실행)
-   [환경 설정](#환경-설정)
-   [검색 시스템](#검색-시스템)
-   [이벤트 처리](#이벤트-처리)

## 🎯 개요

VYBZ Search Service는 다음과 같은 기능을 제공합니다:

-   **버스커 검색**: 닉네임 기반 버스커 검색 (팔로워 수 순 정렬)
-   **릴스 검색**: 내용 및 해시태그 기반 릴스 검색 (최신순 정렬)
-   **해시태그 검색**: 해시태그 키워드 기반 검색 (최대 60개)
-   **무한스크롤**: 커서 기반 무한스크롤 검색 결과
-   **이벤트 처리**: Kafka를 통한 버스커 팔로워 수 업데이트
-   **데이터 저장**: Elasticsearch를 통한 검색 데이터 저장
-   **서비스 디스커버리**: Eureka Client를 통한 서비스 등록

## 🛠 기술 스택

### Backend

![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-005571?style=for-the-badge&logo=elasticsearch&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

### Infra

![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### 협업

![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

### Database & Search

-   **Elasticsearch**: 검색 데이터 저장 및 검색 엔진

### Message Queue

-   **Apache Kafka**: 비동기 이벤트 구독

### Documentation

-   **Swagger/OpenAPI 3.0**: API 문서화

### Build & Deploy

-   **Gradle**: 빌드 도구
-   **Docker**: 컨테이너화

## 🚀 주요 기능

### 1. 검색 시스템

-   **버스커 검색**: 닉네임 기반 버스커 검색 (팔로워 수 순 정렬)
-   **릴스 검색**: 내용 및 해시태그 기반 릴스 검색 (최신순 정렬)
-   **해시태그 검색**: 해시태그 키워드 기반 검색 (최대 60개)
-   **무한스크롤**: 커서 기반 무한스크롤로 효율적인 검색 결과 조회
-   **성능 최적화**: Elasticsearch 인덱싱을 통한 빠른 검색 성능

### 2. 검색 타입 지원

-   **버스커 검색**: 닉네임, 닉네임 초성, 프로필 이미지, 팔로워 수
-   **릴스 검색**: 작성자 UUID, 내용, 해시태그, 썸네일 URL, 생성 시간
-   **해시태그 검색**: 해시태그 키워드 기반 검색

### 3. 이벤트 처리

-   **버스커 팔로워 수 업데이트**: 팔로워 수 변경 시 검색 데이터 동기화

## 📁 프로젝트 구조

```
src/main/java/back/vybz/search_service/
├── common/                    # 공통 모듈
│   ├── config/               # 설정 클래스들
│   │   └── SwaggerConfig.java
│   ├── entity/               # 공통 엔티티
│   │   ├── BaseResponseEntity.java
│   │   └── BaseResponseStatus.java
│   ├── exception/            # 예외 처리
│   │   ├── AsyncExceptionHandler.java
│   │   ├── BaseException.java
│   │   ├── BaseExceptionHandler.java
│   │   ├── BaseExceptionHandlerFilter.java
│   │   └── BaseResponseStatus.java
│   └── util/                 # 유틸리티
│       └── CursorPage.java
├── kafka/                    # Kafka 이벤트 처리
│   ├── config/               # Kafka 설정
│   │   ├── BuskerFollowerCountEventConfig.java
│   │   └── CommonKafkaConfig.java
│   ├── consumer/             # 이벤트 컨슈머
│   │   └── BuskerFollowerCountEventConsumer.java
│   └── event/                # 이벤트 모델
│       └── BuskerFollowerCountEvent.java
├── busker/                   # 버스커 검색 도메인
│   ├── application/          # 버스커 검색 서비스 로직
│   │   ├── service/
│   │   │   ├── BuskerIndexInitializer.java
│   │   │   ├── BuskerSearchService.java
│   │   │   └── BuskerSearchServiceImpl.java
│   ├── domain/               # 버스커 도메인 모델
│   │   └── BuskerSearchDocument.java
│   ├── dto/                  # 버스커 DTO
│   │   ├── request/
│   │   │   └── RequestScrollSearchBuskerDto.java
│   │   └── response/
│   │       └── ResponseScrollSearchBuskerDto.java
│   ├── infrastructure/       # 버스커 리포지토리
│   │   └── BuskerSearchDocumentRepository.java
│   ├── presentation/         # 버스커 컨트롤러
│   │   └── BuskerSearchController.java
│   └── vo/                   # 버스커 VO
│       ├── request/
│       │   └── RequestScrollSearchBuskerVo.java
│       └── response/
│           └── ResponseScrollSearchBuskerVo.java
├── feed/                     # 피드 검색 도메인
│   ├── application/          # 피드 검색 서비스 로직
│   │   ├── service/
│   │   │   ├── HashTagSearchService.java
│   │   │   ├── HashTagSearchServiceImpl.java
│   │   │   ├── ReelsIndexInitializer.java
│   │   │   ├── ReelsSearchService.java
│   │   │   └── ReelsSearchServiceImpl.java
│   ├── domain/               # 피드 도메인 모델
│   │   └── ReelsDocument.java
│   ├── dto/                  # 피드 DTO
│   │   ├── request/
│   │   │   ├── RequestScrollSearchReelsDto.java
│   │   │   └── RequestSearchHashTagDto.java
│   │   └── response/
│   │       ├── ResponseScrollSearchReelsDto.java
│   │       └── ResponseSearchHashTagDto.java
│   ├── infrastructure/       # 피드 리포지토리
│   │   └── ReelsDocumentRepository.java
│   ├── presentation/         # 피드 컨트롤러
│   │   ├── HashTagSearchController.java
│   │   └── ReelsController.java
│   └── vo/                   # 피드 VO
│       ├── request/
│       │   ├── RequestScrollSearchReelsVo.java
│       │   └── RequestSearchHashTagVo.java
│       └── response/
│           ├── ResponseScrollSearchReelsVo.java
│           └── ResponseSearchHashTagVo.java
└── SearchServiceApplication.java
```

## 📚 API 문서

Swagger UI를 통해 API 문서를 확인할 수 있습니다:

-   **URL**: `http://localhost:8000/search-service/swagger-ui/index.html`
-   **API 그룹**: BUSKER-SEARCH-SERVICE, FEED-SEARCH-SERVICE, HASH-TAG-SEARCH

### 주요 API 엔드포인트

#### 검색 API

-   `GET /api/v1/search/buskers` - 버스커 검색 (무한스크롤)
-   `GET /api/v1/search/reels` - 릴스 검색 (무한스크롤)
-   `GET /api/v1/search/hashtags` - 해시태그 검색

### API 요청/응답 예시

#### 버스커 검색 요청

```
GET /api/v1/search/buskers?keyword=버스커&size=10
```

#### 릴스 검색 요청

```
GET /api/v1/search/reels?keyword=음악&size=10
```

#### 해시태그 검색 요청

```
GET /api/v1/search/hashtags?keyword=재즈
```

#### 응답 예시

```json
{
    "status": "SUCCESS",
    "message": "검색 성공",
    "data": {
        "items": [
            {
                "buskerUuid": "busker-uuid-123",
                "nickname": "재즈버스커",
                "nicknameChosung": "ㅈㅈㅂㅅㅋ",
                "profileImageUrl": "https://example.com/profile.jpg",
                "followerCount": 1500
            }
        ],
        "hasNext": true,
        "lastId": "busker-uuid-123"
    }
}
```

## 🚀 설치 및 실행

### 1. 사전 요구사항

-   Java 17
-   Gradle 8.4+
-   Docker (선택사항)
-   Elasticsearch 8.13+
-   Kafka 3.0+

### 2. 로컬 실행

```bash
# 프로젝트 클론
git clone <repository-url>
cd vybz-search

# Gradle 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### 3. Docker 실행

```bash
# Docker 이미지 빌드
docker build -t vybz-search .

# Docker 컨테이너 실행
docker run -p 8000:8000 vybz-search
```

## ⚙️ 환경 설정

### 주요 설정 파일

-   `application.yml`: 기본 설정

### 환경 변수

```yaml
# Elasticsearch 설정
spring:
  elasticsearch:
    uris: http://${ELASTICSEARCH_HOST}:${ELASTICSEARCH_PORT}

  kafka:
    bootstrap-servers: ${KAFKA_SERVERS}
```

#### 커스텀 쿼리

```java
// 버스커 검색을 위한 커스텀 쿼리
public List<BuskerSearchDocument> searchBuskers(String keyword, int size) {
    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
        .should(QueryBuilders.matchQuery("nickname", keyword))
        .should(QueryBuilders.matchQuery("nicknameChosung", keyword));
    
    SearchRequest searchRequest = new SearchRequest("create-busker-search");
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(queryBuilder);
    searchSourceBuilder.sort("followerCount", SortOrder.DESC);
    searchSourceBuilder.size(size);
    searchRequest.source(searchSourceBuilder);
    
    return elasticsearchClient.search(searchRequest, BuskerSearchDocument.class);
}
```

## 📡 이벤트 처리

### Kafka 이벤트

#### 구독 이벤트

-   **BuskerFollowerCountEvent**: 버스커 팔로워 수 업데이트 이벤트

    -   `buskerUuid`: 버스커 UUID
    -   `followerCount`: 팔로워 수
    -   `displayFollowerCount`: 표시용 팔로워 수

### 이벤트 컨슈머

-   `BuskerFollowerCountEventConsumer`: 버스커 팔로워 수 업데이트 이벤트 처리

### Kafka 토픽

-   `busker-follower-count-event`: 버스커 팔로워 수 업데이트 이벤트 토픽

### 이벤트 처리 시점

-   **팔로워 수 업데이트**: User Service에서 팔로우/언팔로우 처리 시

## 🏗 아키텍처

### 도메인 주도 설계 (DDD)

-   **Domain Layer**: 검색 도메인 모델과 비즈니스 로직
-   **Application Layer**: 검색 서비스 로직과 유스케이스
-   **Infrastructure Layer**: Elasticsearch 접근과 외부 시스템 연동
-   **Presentation Layer**: REST API 엔드포인트

### 마이크로서비스 패턴

-   **Service Discovery**: Eureka Client를 통한 서비스 등록
-   **Event-Driven**: Kafka를 통한 비동기 이벤트 구독
-   **Stateless**: 상태 없는 서비스 설계

### 데이터베이스 설계

-   **Elasticsearch**: 검색 데이터 저장 및 검색 엔진
-   **인덱싱**: 검색 성능을 위한 인덱스 설정

### 이벤트 기반 아키텍처

-   **이벤트 구독**: User Service의 팔로워 수 변경 이벤트를 구독하여 검색 데이터 동기화
-   **비동기 처리**: Kafka를 통한 비동기 이벤트 처리
-   **데이터 일관성**: 이벤트를 통한 검색 데이터 일관성 유지

## 🔧 개발 가이드

### 코드 컨벤션

-   **패키지 구조**: 도메인별 계층 분리
-   **네이밍**: 명확하고 일관된 네이밍 규칙
-   **예외 처리**: BaseException을 통한 통일된 예외 처리
-   **로깅**: Slf4j를 통한 구조화된 로깅

### 테스트

```bash
# 단위 테스트 실행
./gradlew test

# 통합 테스트 실행
./gradlew integrationTest
```

### 예외 처리

```java
// 검색 관련 예외
public enum BaseResponseStatus {
    SEARCH_FAIL("검색에 실패했습니다."),
    INVALID_KEYWORD("유효하지 않은 검색어입니다."),
    ELASTICSEARCH_ERROR("검색 엔진 오류가 발생했습니다.");
}
```

### 성능 최적화

#### Elasticsearch 최적화

-   **인덱싱**: 검색 필드에 인덱스 설정
-   **커스텀 쿼리**: 검색 성능을 위한 최적화된 쿼리
-   **페이지네이션**: 커서 기반 페이지네이션으로 성능 향상

#### Kafka 최적화

-   **컨슈머 그룹**: 각 이벤트 타입별 컨슈머 그룹 설정
-   **오프셋 관리**: earliest 오프셋으로 메시지 손실 방지
-   **배치 처리**: 메시지 배치 처리로 성능 향상

## 📊 모니터링

### 로깅

-   **애플리케이션 로그**: Spring Boot 로깅
-   **검색 로그**: 검색 작업 로깅
-   **Kafka 로그**: 이벤트 구독 로깅
-   **Elasticsearch 로그**: 검색 쿼리 성능 로깅

### 메트릭

-   **검색량**: 초당 검색 요청 수
-   **응답 시간**: API 응답 시간
-   **에러율**: 에러 발생률
-   **Kafka 메시지**: 이벤트 구독 성공률

### 알림

-   **검색 실패**: 검색 실패 알림
-   **Elasticsearch 오류**: 검색 엔진 연결 오류 알림
-   **Kafka 오류**: 메시지 수신 실패 알림

## 🚨 트러블슈팅

### 일반적인 문제

#### Elasticsearch 연결 실패

```bash
# Elasticsearch 연결 확인
curl -X GET "http://<탄력적 IP>:9200/_cluster/health"

# 인덱스 상태 확인
curl -X GET "http://<탄력적 IP>:9200/_cat/indices?v"
```

#### Kafka 연결 실패

```bash
# Kafka 브로커 상태 확인
kafka-topics.sh --bootstrap-server <탄력적 IP>:10000 --list

# 토픽 상세 정보 확인
kafka-topics.sh --bootstrap-server <탄력적 IP>:10000 --describe --topic busker-follower-count-event
```

#### Eureka 연결 실패

```bash
# Eureka 서버 상태 확인
curl http://eureka:8761/eureka/apps/search-service

# 서비스 등록 확인
curl http://eureka:8761/eureka/apps
```

### 로그 확인

```bash
# 애플리케이션 로그 확인
tail -f logs/application.log

# 에러 로그 확인
grep "ERROR" logs/application.log

# Kafka 로그 확인
grep "Kafka" logs/application.log
```

## 📝 라이선스

이 프로젝트는 VYBZ 팀의 내부 프로젝트입니다.

## 👥 팀

-   **개발팀**: VYBZ Backend Team

---

**VYBZ Search Service** - Elasticsearch 기반 검색 서비스
