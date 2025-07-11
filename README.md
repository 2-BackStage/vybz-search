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
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

### Search & Analytics

![Elasticsearch](https://img.shields.io/badge/Elasticsearch-005571?style=for-the-badge&logo=elasticsearch&logoColor=white)
![Kibana](https://img.shields.io/badge/Kibana-005571?style=for-the-badge&logo=kibana&logoColor=white)
![Elasticsearch Java Client](https://img.shields.io/badge/Elasticsearch_Java_Client-005571?style=for-the-badge&logo=elasticsearch&logoColor=white)

### Message Queue & Streaming

![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Kafka Connect](https://img.shields.io/badge/Kafka_Connect-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Spring Kafka](https://img.shields.io/badge/Spring_Kafka-6DB33F?style=for-the-badge&logo=spring&logoColor=white)

### API Documentation

![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

### Infrastructure & DevOps

![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### Collaboration & Tools

![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

### 주요 기술 상세

#### Search & Analytics

-   **Elasticsearch 8.13.2**: 검색 데이터 저장 및 검색 엔진
-   **Kibana**: Elasticsearch 데이터 시각화 및 모니터링
-   **Elasticsearch Java Client**: Java 기반 Elasticsearch 클라이언트
-   **Nori Analyzer**: 한글 형태소 분석기
-   **Edge N-gram**: 자동완성 기능을 위한 토크나이저

#### Message Queue & Streaming

-   **Apache Kafka 3.0+**: 비동기 이벤트 구독 및 스트리밍
-   **Kafka Connect**: 데이터 파이프라인 구축
-   **Spring Kafka**: Spring 기반 Kafka 통합
-   **JSON Deserializer**: JSON 형태 이벤트 처리

#### Service Discovery & Communication

-   **Spring Cloud Netflix Eureka**: 서비스 디스커버리
-   **Spring Boot 3.4.5**: 애플리케이션 프레임워크
-   **Spring Cloud 2024.0.1**: 마이크로서비스 인프라

#### Development Tools

-   **Lombok**: 보일러플레이트 코드 제거
-   **Swagger/OpenAPI 3.0**: API 문서화
-   **Gradle**: 빌드 및 의존성 관리

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

### 4. 검색 기능 상세

#### 버스커 검색 기능

-   **닉네임 검색**: 정확한 닉네임 매칭 및 부분 검색
-   **초성 검색**: 한글 초성 입력으로 버스커 검색
-   **자동완성**: Edge N-gram을 통한 실시간 자동완성
-   **정렬**: 팔로워 수 내림차순 정렬
-   **페이지네이션**: 커서 기반 무한스크롤

#### 릴스 검색 기능

-   **내용 검색**: 릴스 내용 기반 검색
-   **해시태그 검색**: 해시태그 기반 검색
-   **작성자 검색**: 작성자 UUID 기반 검색
-   **정렬**: 생성 시간 내림차순 정렬
-   **페이지네이션**: 커서 기반 무한스크롤

#### 해시태그 검색 기능

-   **키워드 검색**: 해시태그 키워드 기반 검색
-   **최대 결과**: 최대 60개 결과 반환
-   **실시간 검색**: 실시간 해시태그 추천

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
│       ├── CursorPage.java
│       └── ChosungUtils.java
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
│   │   └── service/
│   │       ├── BuskerIndexInitializer.java
│   │       ├── BuskerSearchService.java
│   │       └── BuskerSearchServiceImpl.java
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
│   │   └── service/
│   │       ├── HashTagSearchService.java
│   │       ├── HashTagSearchServiceImpl.java
│   │       ├── ReelsIndexInitializer.java
│   │       ├── ReelsSearchService.java
│   │       └── ReelsSearchServiceImpl.java
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

### API 상세 스펙

#### 버스커 검색 API

**엔드포인트**: `GET /api/v1/search/buskers`

**쿼리 파라미터**:

-   `keyword` (String, 선택): 검색 키워드
-   `size` (Integer, 선택): 페이지 크기 (기본값: 10)
-   `lastId` (String, 선택): 마지막 검색 결과 ID (커서 페이지네이션)

**응답 예시**:

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

#### 릴스 검색 API

**엔드포인트**: `GET /api/v1/search/reels`

**쿼리 파라미터**:

-   `keyword` (String, 선택): 검색 키워드
-   `size` (Integer, 선택): 페이지 크기 (기본값: 10)
-   `lastId` (String, 선택): 마지막 검색 결과 ID (커서 페이지네이션)

**응답 예시**:

```json
{
    "status": "SUCCESS",
    "message": "검색 성공",
    "data": {
        "items": [
            {
                "feedId": "feed-uuid-123",
                "writerUuid": "user-uuid-456",
                "content": "재즈 음악 연주",
                "hashTag": ["재즈", "음악", "연주"],
                "thumbnailUrl": "https://example.com/thumbnail.jpg",
                "createdAt": 1640995200000
            }
        ],
        "hasNext": true,
        "lastId": "feed-uuid-123"
    }
}
```

#### 해시태그 검색 API

**엔드포인트**: `GET /api/v1/search/hashtags`

**쿼리 파라미터**:

-   `keyword` (String, 필수): 검색 키워드

**응답 예시**:

```json
{
    "status": "SUCCESS",
    "message": "검색 성공",
    "data": {
        "hashtags": ["재즈", "재즈음악", "재즈버스커", "재즈클럽"]
    }
}
```

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

-   **Java 17**: OpenJDK 17 이상
-   **Gradle 8.4+**: 빌드 도구
-   **Docker** (선택사항): 컨테이너화
-   **Elasticsearch 8.13+**: 검색 엔진
-   **Kibana**: Elasticsearch 시각화 도구
-   **Apache Kafka 3.0+**: 메시지 큐
-   **Kafka Connect**: 데이터 파이프라인
-   **Spring Cloud Eureka**: 서비스 디스커버리

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

### 4. 인프라 구성

#### Elasticsearch & Kibana 설정

```bash
# Elasticsearch 실행
docker run -d \
  --name elasticsearch \
  -p 9200:9200 \
  -p 9300:9300 \
  -e "discovery.type=single-node" \
  -e "xpack.security.enabled=false" \
  docker.elastic.co/elasticsearch/elasticsearch:8.13.2

# Kibana 실행
docker run -d \
  --name kibana \
  -p 5601:5601 \
  -e "ELASTICSEARCH_HOSTS=http://localhost:9200" \
  docker.elastic.co/kibana/kibana:8.13.2
```

#### Kafka & Kafka Connect 설정

```bash
# Kafka 실행 (Zookeeper 포함)
docker run -d \
  --name kafka \
  -p 9092:9092 \
  -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  confluentinc/cp-kafka:latest

# Kafka Connect 실행
docker run -d \
  --name kafka-connect \
  -p 8083:8083 \
  -e CONNECT_BOOTSTRAP_SERVERS=localhost:9092 \
  -e CONNECT_REST_PORT=8083 \
  confluentinc/cp-kafka-connect:latest
```

#### Eureka 서버 설정

```bash
# Eureka 서버 실행
docker run -d \
  --name eureka-server \
  -p 8761:8761 \
  springcloud/eureka:latest
```

## ⚙️ 환경 설정

### 주요 설정 파일

-   `application.yml`: 기본 설정
-   `build.gradle`: 의존성 및 빌드 설정

### 애플리케이션 설정

```yaml
spring:
    application:
        name: search-service

    elasticsearch:
        uris: http://<탄력적 IP>:9200 # Elasticsearch 서버

    kafka:
        bootstrap-servers: <탄력적 IP>:10000,<탄력적 IP>:10001,<탄력적IP>:10002 # Kafka 클러스터
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

-   `busker-follower-count`: 버스커 팔로워 수 업데이트 이벤트 토픽

### 이벤트 처리 시점

-   **팔로워 수 업데이트**: User Service에서 팔로우/언팔로우 처리 시

```

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

-   **패키지 구조**: 도메인별 계층 분리 (DDD 패턴)
-   **네이밍**: 명확하고 일관된 네이밍 규칙
-   **예외 처리**: BaseException을 통한 통일된 예외 처리
-   **로깅**: Slf4j를 통한 구조화된 로깅
-   **Lombok**: 보일러플레이트 코드 제거

### 아키텍처 패턴

#### 도메인 주도 설계 (DDD)

```
busker/                    # 버스커 도메인
├── application/           # 애플리케이션 서비스
├── domain/               # 도메인 모델
├── dto/                  # 데이터 전송 객체
├── infrastructure/       # 인프라스트럭처
├── presentation/         # 프레젠테이션 계층
└── vo/                   # 뷰 객체
```

#### 계층별 역할

-   **Presentation Layer**: REST API 엔드포인트, 요청/응답 처리
-   **Application Layer**: 비즈니스 로직, 유스케이스 구현
-   **Domain Layer**: 도메인 모델, 비즈니스 규칙
-   **Infrastructure Layer**: 외부 시스템 연동, 데이터 접근

### 테스트

```bash
# 단위 테스트 실행
./gradlew test

# 통합 테스트 실행
./gradlew integrationTest

# 테스트 커버리지 확인
./gradlew jacocoTestReport
```

### 예외 처리

```java
// 검색 관련 예외
public enum BaseResponseStatus {
    SEARCH_FAIL("검색에 실패했습니다."),
    INVALID_KEYWORD("유효하지 않은 검색어입니다."),
    ELASTICSEARCH_ERROR("검색 엔진 오류가 발생했습니다."),
    KAFKA_CONSUMER_ERROR("이벤트 처리 중 오류가 발생했습니다."),
    INDEX_NOT_FOUND("검색 인덱스를 찾을 수 없습니다.");
}

// 예외 처리 예시
@ExceptionHandler(BaseException.class)
public ResponseEntity<BaseResponseEntity> handleBaseException(BaseException e) {
    return ResponseEntity.status(e.getStatus().getHttpStatus())
        .body(BaseResponseEntity.error(e.getStatus()));
}
```

### 로깅 가이드

```java
// 로깅 레벨별 사용
log.debug("🔍 [검색 요청] keyword='{}'", keyword);  // 디버그 정보
log.info("✅ ES 업데이트 완료: {}", response.result());  // 성공 정보
log.warn("⚠️ ES 업데이트 실패: 해당 buskerUuid 없음 → skip");  // 경고
log.error("❌ ES 업데이트 실패", e);  // 에러
```

### 개발 환경 설정

#### IDE 설정

-   **IntelliJ IDEA**: Spring Boot 플러그인 설치
-   **Eclipse**: Spring Tools Suite (STS) 설치
-   **VS Code**: Java Extension Pack 설치

#### Git 설정

```bash
# 브랜치 전략
main          # 프로덕션 브랜치
develop       # 개발 브랜치
feature/*     # 기능 개발 브랜치
hotfix/*      # 긴급 수정 브랜치
```

### 성능 최적화

#### Elasticsearch 최적화

-   **인덱싱**: 검색 필드에 인덱스 설정
-   **커스텀 쿼리**: 검색 성능을 위한 최적화된 쿼리
-   **페이지네이션**: 커서 기반 페이지네이션으로 성능 향상
-   **한글 분석기**: Nori 형태소 분석기를 통한 정확한 한글 검색
-   **자동완성**: Edge N-gram 토크나이저를 통한 자동완성 기능
-   **초성 검색**: 한글 초성 검색 지원

#### 검색 분석기 설정

```json
{
    "analyzer": {
        "edge_ngram_analyzer": {
            "type": "custom",
            "tokenizer": "custom_edge_ngram_tokenizer",
            "filter": ["lowercase"]
        },
        "nori_analyzer": {
            "type": "custom",
            "tokenizer": "nori_tokenizer",
            "filter": ["lowercase"]
        }
    },
    "tokenizer": {
        "custom_edge_ngram_tokenizer": {
            "type": "edge_ngram",
            "min_gram": 1,
            "max_gram": 20,
            "token_chars": ["letter", "digit"]
        }
    }
}
```

#### Kafka 최적화

-   **컨슈머 그룹**: 각 이벤트 타입별 컨슈머 그룹 설정
-   **오프셋 관리**: earliest 오프셋으로 메시지 손실 방지
-   **배치 처리**: 메시지 배치 처리로 성능 향상

## 📊 모니터링

### Elasticsearch & Kibana 모니터링

#### Kibana 대시보드

-   **URL**: `http://13.124.91.96:5601`
-   **인덱스 모니터링**: `create-busker-search`, `create-reels-search`
-   **검색 성능 대시보드**: 검색 쿼리 응답 시간 및 처리량
-   **인덱스 상태**: 샤드 상태, 문서 수, 저장소 사용량

#### Elasticsearch 모니터링

-   **클러스터 헬스**: `GET /_cluster/health`
-   **인덱스 상태**: `GET /_cat/indices?v`
-   **검색 성능**: 쿼리 실행 시간 및 처리량
-   **메모리 사용량**: 힙 메모리 및 오프힙 메모리

### Kafka 모니터링

#### Kafka Connect 모니터링

-   **커넥터 상태**: 데이터 파이프라인 상태 확인
-   **토픽 모니터링**: `busker-follower-count` 토픽 상태
-   **컨슈머 그룹**: `search-service-group` 오프셋 및 지연량

#### Kafka 메트릭

-   **메시지 처리량**: 초당 처리 메시지 수
-   **컨슈머 지연**: 메시지 처리 지연 시간
-   **브로커 상태**: 클러스터 브로커 상태

### 애플리케이션 모니터링

#### 로깅

-   **애플리케이션 로그**: Spring Boot 로깅
-   **검색 로그**: 검색 작업 로깅
-   **Kafka 로그**: 이벤트 구독 로깅
-   **Elasticsearch 로그**: 검색 쿼리 성능 로깅

#### 메트릭

-   **검색량**: 초당 검색 요청 수
-   **응답 시간**: API 응답 시간
-   **에러율**: 에러 발생률
-   **Kafka 메시지**: 이벤트 구독 성공률

### 알림 시스템

-   **검색 실패**: 검색 실패 알림
-   **Elasticsearch 오류**: 검색 엔진 연결 오류 알림
-   **Kafka 오류**: 메시지 수신 실패 알림
-   **인덱스 상태**: 인덱스 상태 이상 알림

## 🚨 트러블슈팅

### 일반적인 문제

#### Elasticsearch 연결 실패

```bash
# Elasticsearch 연결 확인
curl -X GET "http://13.124.91.96:9200/_cluster/health"

# 인덱스 상태 확인
curl -X GET "http://13.124.91.96:9200/_cat/indices?v"

# 특정 인덱스 상태 확인
curl -X GET "http://13.124.91.96:9200/create-busker-search/_stats"
curl -X GET "http://13.124.91.96:9200/create-reels-search/_stats"
```

#### Kibana 접속 문제

```bash
# Kibana 서비스 상태 확인
curl -X GET "http://13.124.91.96:5601/api/status"

# Elasticsearch 연결 확인 (Kibana에서)
curl -X GET "http://13.124.91.96:5601/api/elasticsearch/status"
```

#### Kafka 연결 실패

```bash
# Kafka 브로커 상태 확인
kafka-topics.sh --bootstrap-server 13.124.91.96:10000 --list

# 토픽 상세 정보 확인
kafka-topics.sh --bootstrap-server 13.124.91.96:10000 --describe --topic busker-follower-count

# 컨슈머 그룹 상태 확인
kafka-consumer-groups.sh --bootstrap-server 13.124.91.96:10000 --describe --group search-service-group
```

#### Kafka Connect 문제

```bash
# Kafka Connect 상태 확인
curl -X GET "http://13.124.91.96:8083/connectors"

# 커넥터 상태 확인
curl -X GET "http://13.124.91.96:8083/connectors/{connector-name}/status"

# 커넥터 설정 확인
curl -X GET "http://13.124.91.96:8083/connectors/{connector-name}/config"
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

## 🚀 배포 및 운영

### CI/CD 파이프라인

#### GitHub Actions 워크플로우

```yaml
name: CI/CD Docker Deploy to EC2 search-Service

on:
    push:
        branches: [dev]

jobs:
    build:
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
              uses: actions/checkout@v4

            - name: Log in to Docker Hub
              uses: docker/login-action@v3
              with:
                  username: ${{ secrets.DOCKER_HUB_USERNAME }}
                  password: ${{ secrets.DOCKER_HUB_PASSWORD }}

            - name: Build & Push search-service
              run: |
                  mkdir -p src/main/resources 
                  echo "${{ secrets.SEARCH_APPLICATION_YML }}" | base64 -d > src/main/resources/application.yml
                  docker build -t ${{ secrets.DOCKER_HUB_USERNAME }}/search-service:${{ github.sha }} .
                  docker push ${{ secrets.DOCKER_HUB_USERNAME }}/search-service:${{ github.sha }}
```

### 배포 환경

#### 프로덕션 환경

-   **서버**: Amazon EC2
-   **컨테이너**: Docker
-   **레지스트리**: Docker Hub
-   **서비스 디스커버리**: Eureka Server
-   **검색 엔진**: Elasticsearch 8.13.2
-   **메시지 큐**: Apache Kafka 3.0+

#### 환경별 설정

```yaml
# 개발 환경
spring:
  profiles: dev
  elasticsearch:
    uris: http://localhost:9200
  kafka:
    bootstrap-servers: localhost:9092

# 프로덕션 환경
spring:
  profiles: prod
  elasticsearch:
    uris: http://13.124.91.96:9200
  kafka:
    bootstrap-servers: 13.124.91.96:10000,13.124.91.96:10001,13.124.91.96:10002
```

### 운영 모니터링

#### 헬스 체크

```bash
# 애플리케이션 헬스 체크
curl -X GET "http://localhost:8000/actuator/health"

# Elasticsearch 헬스 체크
curl -X GET "http://13.124.91.96:9200/_cluster/health"

# Kafka 헬스 체크
kafka-topics.sh --bootstrap-server 13.124.91.96:10000 --list
```

#### 로그 관리

```bash
# 애플리케이션 로그 확인
docker logs -f search-service

# Elasticsearch 로그 확인
docker logs -f elasticsearch

# Kafka 로그 확인
docker logs -f kafka
```

### 백업 및 복구

#### Elasticsearch 백업

```bash
# 인덱스 백업
curl -X PUT "http://13.124.91.96:9200/_snapshot/backup_repo/snapshot_$(date +%Y%m%d)" \
  -H "Content-Type: application/json" \
  -d '{
    "indices": ["create-busker-search", "create-reels-search"]
  }'

# 인덱스 복구
curl -X POST "http://13.124.91.96:9200/_snapshot/backup_repo/snapshot_20240101/_restore" \
  -H "Content-Type: application/json" \
  -d '{
    "indices": ["create-busker-search", "create-reels-search"]
  }'
```

## 📈 성능 최적화

### Elasticsearch 최적화

#### 인덱스 설정 최적화

```json
{
    "settings": {
        "number_of_shards": 3,
        "number_of_replicas": 1,
        "refresh_interval": "1s",
        "max_result_window": 10000
    }
}
```

#### 쿼리 최적화

-   **필터링**: `term` 쿼리 사용으로 캐싱 활용
-   **정렬**: `doc_values` 활용한 효율적인 정렬
-   **페이지네이션**: `search_after` 사용으로 깊은 페이지네이션 최적화

### Kafka 최적화

#### 컨슈머 설정 최적화

```properties
# 배치 처리 설정
spring.kafka.consumer.max-poll-records=500
spring.kafka.consumer.fetch-min-size=1
spring.kafka.consumer.fetch-max-wait=500

# 오프셋 관리
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.enable-auto-commit=false
```

## 🔒 보안

### Elasticsearch 보안

-   **네트워크 보안**: 방화벽을 통한 접근 제한
-   **인증**: X-Pack Security 설정 (필요시)
-   **SSL/TLS**: HTTPS 통신 설정

### Kafka 보안

-   **SASL 인증**: 사용자 인증 설정
-   **SSL/TLS**: 암호화 통신 설정
-   **ACL**: 토픽별 접근 제어

## 📝 라이선스

이 프로젝트는 VYBZ 팀의 내부 프로젝트입니다.

## 👥 팀

-   **개발팀**: VYBZ Backend Team
-   **기술 스택**: Spring Boot, Elasticsearch, Kafka, Docker
-   **협업 도구**: Discord, Notion, Git

---

**VYBZ Search Service** - Elasticsearch 기반 검색 서비스
