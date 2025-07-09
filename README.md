# 🎵 VYBZ Search Service

> **VYBZ 플랫폼의 검색 서비스** - 버스커, 릴스, 해시태그 검색을 위한 고성능 검색 엔진

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Elasticsearch](https://img.shields.io/badge/Elasticsearch-8.13.2-yellow.svg)](https://www.elastic.co/elasticsearch/)
[![Kafka](https://img.shields.io/badge/Kafka-3.x-black.svg)](https://kafka.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)

---

## 📋 Overview

VYBZ Search Service는 VYBZ 플랫폼의 핵심 검색 기능을 담당하는 마이크로서비스입니다. Elasticsearch를 기반으로 한 고성능 검색 엔진을 통해 버스커, 릴스, 해시태그 검색을 제공하며, 무한스크롤과 실시간 업데이트를 지원합니다.

### ✨ 주요 기능
- 🔍 **버스커 검색**: 닉네임 기반 검색, 팔로워 수 순 정렬
- 🎬 **릴스 검색**: 내용 및 해시태그 기반 검색, 최신순 정렬
- 🏷️ **해시태그 검색**: 키워드 기반 해시태그 검색 (최대 60개)
- 📜 **무한스크롤**: 커서 기반 페이지네이션 지원
- ⚡ **실시간 업데이트**: Kafka를 통한 실시간 데이터 동기화

---

## 🛠 Tech Stack

| Category | Technology | Version |
|----------|------------|---------|
| **Language** | Java | 17 |
| **Framework** | Spring Boot | 3.4.5 |
| **Search Engine** | Elasticsearch | 8.13.2 |
| **Message Queue** | Apache Kafka | 3.x |
| **Service Discovery** | Netflix Eureka | 2024.0.1 |
| **API Documentation** | Swagger/OpenAPI | 2.7.0 |
| **Build Tool** | Gradle | 8.4 |
| **Container** | Docker | Latest |

---

## 🏗️ 서비스 목록

| 서비스 | 설명 | 언어 | 상태 |
|--------|------|------|------|
| **Search Service** | 버스커, 릴스, 해시태그 검색 API | Java 17 | ✅ Active |
| **Busker Search** | 버스커 인물 검색 (닉네임 기반) | Java 17 | ✅ Active |
| **Reels Search** | 릴스 콘텐츠 검색 (내용/해시태그 기반) | Java 17 | ✅ Active |
| **HashTag Search** | 해시태그 키워드 검색 | Java 17 | ✅ Active |

---

## 📌 Architecture Diagram

> 🚧 **아키텍처 다이어그램이 곧 추가될 예정입니다.**

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client Apps   │    │   API Gateway   │    │  Search Service │
│                 │◄──►│                 │◄──►│                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                       │
                                                       ▼
                                              ┌─────────────────┐
                                              │  Elasticsearch  │
                                              │                 │
                                              └─────────────────┘
                                                       ▲
                                                       │
                                              ┌─────────────────┐
                                              │     Kafka       │
                                              │                 │
                                              └─────────────────┘
```

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Gradle 8.4+
- Elasticsearch 8.13.2
- Apache Kafka 3.x
- Docker (선택사항)

### 1. Clone Repository
```bash
git clone https://github.com/your-org/vybz-search.git
cd vybz-search
```

### 2. Environment Setup
```bash
# Elasticsearch 실행 (Docker)
docker run -d --name elasticsearch \
  -p 9200:9200 -p 9300:9300 \
  -e "discovery.type=single-node" \
  -e "xpack.security.enabled=false" \
  docker.elastic.co/elasticsearch/elasticsearch:8.13.2

# Kafka 실행 (Docker)
docker run -d --name kafka \
  -p 9092:9092 \
  -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  confluentinc/cp-kafka:latest
```

### 3. Build & Run
```bash
# Gradle 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### 4. Docker 실행 (선택사항)
```bash
# Docker 이미지 빌드
docker build -t vybz-search .

# 컨테이너 실행
docker run -p 8080:8080 vybz-search
```

---

## 📁 Project Structure

```
vybz-search/
├── 📄 build.gradle                 # Gradle 빌드 설정
├── 📄 Dockerfile                   # Docker 컨테이너 설정
├── 📄 gradlew                      # Gradle Wrapper
├── 📄 gradlew.bat                  # Gradle Wrapper (Windows)
├── 📄 settings.gradle              # Gradle 프로젝트 설정
│
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/back/vybz/search_service/
│   │   │   ├── 🚀 SearchServiceApplication.java    # 메인 애플리케이션
│   │   │   │
│   │   │   ├── 📁 busker/                          # 버스커 검색 모듈
│   │   │   │   ├── 📁 application/
│   │   │   │   │   └── 📁 service/
│   │   │   │   │       ├── BuskerIndexInitializer.java
│   │   │   │   │       ├── BuskerSearchService.java
│   │   │   │   │       └── BuskerSearchServiceImpl.java
│   │   │   │   ├── 📁 domain/
│   │   │   │   │   └── BuskerSearchDocument.java
│   │   │   │   ├── 📁 dto/
│   │   │   │   │   ├── 📁 request/
│   │   │   │   │   └── 📁 response/
│   │   │   │   ├── 📁 infrastructure/
│   │   │   │   │   └── BuskerSearchDocumentRepository.java
│   │   │   │   ├── 📁 presentation/
│   │   │   │   │   └── BuskerSearchController.java
│   │   │   │   └── 📁 vo/
│   │   │   │       ├── 📁 request/
│   │   │   │       └── 📁 response/
│   │   │   │
│   │   │   ├── 📁 common/                          # 공통 모듈
│   │   │   │   ├── 📁 config/
│   │   │   │   │   └── SwaggerConfig.java
│   │   │   │   ├── 📁 entity/
│   │   │   │   │   ├── BaseResponseEntity.java
│   │   │   │   │   └── BaseResponseStatus.java
│   │   │   │   ├── 📁 exception/
│   │   │   │   │   ├── AsyncExceptionHandler.java
│   │   │   │   │   ├── BaseException.java
│   │   │   │   │   ├── BaseExceptionHandler.java
│   │   │   │   │   └── BaseExceptionHandlerFilter.java
│   │   │   │   └── 📁 util/
│   │   │   │       ├── ChosungUtils.java
│   │   │   │       └── CursorPage.java
│   │   │   │
│   │   │   ├── 📁 feed/                            # 피드 검색 모듈
│   │   │   │   ├── 📁 application/
│   │   │   │   │   └── 📁 service/
│   │   │   │   │       ├── HashTagSearchService.java
│   │   │   │   │       ├── HashTagSearchServiceImpl.java
│   │   │   │   │       ├── ReelsIndexInitializer.java
│   │   │   │   │       ├── ReelsSearchService.java
│   │   │   │   │       └── ReelsSearchServiceImpl.java
│   │   │   │   ├── 📁 domain/
│   │   │   │   │   └── ReelsDocument.java
│   │   │   │   ├── 📁 dto/
│   │   │   │   │   ├── 📁 request/
│   │   │   │   │   └── 📁 response/
│   │   │   │   ├── 📁 infrastructure/
│   │   │   │   │   └── ReelsDocumentRepository.java
│   │   │   │   ├── 📁 presentation/
│   │   │   │   │   ├── HashTagSearchController.java
│   │   │   │   │   └── ReelsController.java
│   │   │   │   └── 📁 vo/
│   │   │   │       ├── 📁 request/
│   │   │   │       └── 📁 response/
│   │   │   │
│   │   │   └── 📁 kafka/                           # Kafka 모듈
│   │   │       ├── 📁 config/
│   │   │       │   ├── BuskerFollowerCountEventConfig.java
│   │   │       │   └── CommonKafkaConfig.java
│   │   │       ├── 📁 consumer/
│   │   │       │   └── BuskerFollowerCountEventConsumer.java
│   │   │       └── 📁 event/
│   │   │           └── BuskerFollowerCountEvent.java
│   │   │
│   │   └── 📁 resources/                           # 리소스 파일
│   │       └── application.yml
│   │
│   └── 📁 test/                                    # 테스트 코드
│       └── 📁 java/back/vybz/search_service/
│           └── SearchServiceApplicationTests.java
│
└── 📁 build/                                       # 빌드 결과물
```

---

## 🔗 API Endpoints

### Busker Search API
```http
GET /api/v1/search/buskers
```
- **설명**: 버스커 인물 검색 (무한스크롤)
- **정렬**: 팔로워 수 순
- **파라미터**: `keyword`, `cursor`, `size`

### Reels Search API
```http
GET /api/v1/search/reels
```
- **설명**: 릴스 콘텐츠 검색 (무한스크롤)
- **정렬**: 최신순
- **파라미터**: `keyword`, `cursor`, `size`

### HashTag Search API
```http
GET /api/v1/search/hashtags
```
- **설명**: 해시태그 키워드 검색
- **결과**: 최대 60개
- **파라미터**: `keyword`

---

## 📚 Documentation

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/v3/api-docs`

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 📞 Contact

- **Project Link**: [https://github.com/your-org/vybz-search](https://github.com/your-org/vybz-search)
- **Issues**: [https://github.com/your-org/vybz-search/issues](https://github.com/your-org/vybz-search/issues)

---

<div align="center">

**Made with ❤️ by VYBZ Team**

[![GitHub stars](https://img.shields.io/github/stars/your-org/vybz-search?style=social)](https://github.com/your-org/vybz-search/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/your-org/vybz-search?style=social)](https://github.com/your-org/vybz-search/network/members)
[![GitHub issues](https://img.shields.io/github/issues/your-org/vybz-search)](https://github.com/your-org/vybz-search/issues)

</div> 