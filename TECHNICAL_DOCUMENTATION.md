## Technical Documentation: global-application-suite

This document provides a technical overview of the `global-application-suite` project's backend component.

### 1. Technology Stack Analysis

*   **Frameworks/Libraries:**
    *   Spring Boot: **2.7.2** (Based on `pom.xml`)
    *   Spring MVC: Included via `spring-boot-starter-web`. Used for REST controllers.
    *   View Layer: **Angular** (Separate UI project in `src/main/UI`, built and copied to `static` resources via Maven plugin). Spring Boot serves the static Angular files and provides the backend REST API.
    *   Data Access: **Spring Data JPA** (via `spring-boot-starter-data-jpa`)
    *   Persistence Provider: **Hibernate** (Default provider for Spring Data JPA)
    *   Database Driver: **H2** (Configured in `application.properties`), **MySQL Connector/J 8.0.29** (Present in `pom.xml`, but H2 seems to be the active configuration for development).
    *   Other Key Libraries:
        *   Lombok: Used for reducing boilerplate code (e.g., getters, setters).
        *   Spring Boot Validation: Included via `spring-boot-starter-validation`.
        *   Jackson: Default JSON processor for Spring Web (implicitly included).
*   **Build Tool:** **Maven** (Based on `pom.xml` and `mvnw` files)
*   **Database Configuration:**
    *   Database Type: **H2 (File-based)**
    *   Configuration Source: `src/main/resources/application.properties`
    *   Key Properties:
        *   `spring.datasource.url=jdbc:h2:file:~/spring-boot-h2-d387F` (Connects to a file-based H2 database)
        *   `spring.datasource.username=sa`
        *   `spring.datasource.password=` (empty)
        *   `spring.datasource.driverClassName=org.h2.Driver`
    *   JPA/Hibernate:
        *   `spring.jpa.hibernate.ddl-auto=update` (Updates schema automatically, suitable for development)
        *   `spring.jpa.show-sql=true` (Logs executed SQL statements)
        *   `spring.jpa.properties.hibernate.format_sql=true` (Formats logged SQL)
    *   H2 Console: Enabled at `/h2-console` (`spring.h2.console.enabled=true`)
*   **Security Implementation:**
    *   *(Cannot determine from provided files. Requires analysis of `config` package or specific security-related classes, if any exist.)* Likely none configured by default unless specific Spring Security starters/configurations are present.
*   **Testing Frameworks:**
    *   Unit/Integration Testing: **Spring Boot Test** (via `spring-boot-starter-test`), likely using **JUnit 5** and **Mockito** (defaults with the starter).

### 2. Project Structure Breakdown

*   **Directory Tree Analysis:**
    ```plaintext
    global-application-suite/
    ├── pom.xml                 # Maven build configuration
    ├── src/
    │   ├── main/
    │   │   ├── java/                 # Root package for backend source
    │   │   │   ├── D387SampleCodeApplication.java # Main class
    │   │   │   ├── ServletInitializer.java # For WAR deployment (if needed)
    │   │   │   ├── H2Bootstrap.java      # Likely data seeding/init
    │   │   │   ├── config/             # Spring configuration classes
    │   │   │   ├── convertor/          # DTO/Entity mapping & Services
    │   │   │   ├── entity/             # JPA Entities
    │   │   │   ├── model/              # DTOs or other models
    │   │   │   ├── repository/         # Spring Data JPA Repositories
    │   │   │   └── rest/               # REST Controllers
    │   │   ├── resources/
    │   │   │   ├── static/               # Static web resources (Angular build output)
    │   │   │   ├── application.properties  # Main configuration
    │   │   │   └── welcome*.properties     # Resource bundles for i18n
    │   │   └── UI/                     # Angular frontend source code
    │   └── test/
    │       └── java/
    │           └── edu/wgu/d387_sample_code/ # Backend tests (Path needs update if tests moved)
    └── target/                     # Build output (classes, JARs)
    ```
*   **Package-by-Package Explanation:**
    *   `rest`: Contains Spring MVC REST controllers (`@RestController`) handling incoming HTTP requests for the API consumed by the Angular frontend.
    *   `convertor`: Contains classes responsible for mapping between Entities and DTOs (e.g., using MapStruct or manual mapping), and also currently includes Service interfaces and implementations.
    *   `repository`: Contains Spring Data JPA repository interfaces extending `CrudRepository` or similar.
    *   `entity`: Contains JPA entities annotated with `@Entity`.
    *   `model`: Contains Data Transfer Objects (DTOs) and related classes (`Links`, `Self`) used for API requests/responses.
    *   `config`: Contains Spring configuration classes (`@Configuration`).
    *   `exception`: *(Not listed, but standard practice)* Should contain custom exceptions and potentially `@ControllerAdvice` for global handling.
    *   `H2Bootstrap.java`: Suggests a class run on startup (perhaps implementing `CommandLineRunner` or using `@PostConstruct`) to populate the H2 database with initial data.

### 3. File-by-File Analysis

*   **Main Application Class (`D387SampleCodeApplication.java`):**
    *   Annotated with `@SpringBootApplication`.
    *   Contains the `main` method using `SpringApplication.run()`.
    *   Likely enables component scanning for packages under the root (e.g., `config`, `rest`, `repository`, `convertor`).
*   **Configuration Files (`application.properties`):**
    *   Configures H2 database connection, JPA/Hibernate settings, H2 console.
    *   Disables writing dates as timestamps in Jackson JSON serialization (`spring.jackson.serialization.write_dates_as_timestamps=false`).
*   **Internationalization Files (`welcome*.properties`):**
    *   Contain key-value pairs for supporting multiple languages (English US, French Canadian, default). Used by Spring's `MessageSource`.
*   **Controller Classes (`rest/` package):**
    *   *(Requires code analysis)* Expected to use `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc., to define API endpoints. Will likely inject Services or Repositories. Parameters often annotated with `@PathVariable`, `@RequestParam`, `@RequestBody`. Return types usually `ResponseEntity<T>` where T is a DTO or collection of DTOs.
*   **Service Implementations (`convertor/` package - currently):**
    *   *(Requires code analysis)* Expected to use `@Service`, `@Transactional`. Contain core business logic, call repository methods, perform data manipulation, and potentially map between entities and DTOs (possibly delegating to other `convertor` classes).
*   **Repository Interfaces (`repository/` package):**
    *   *(Requires code analysis)* Expected to extend `CrudRepository<EntityType, IdType>`. May contain custom query methods following Spring Data naming conventions or using `@Query`.
*   **Entity Relationships (`entity/` package):**
    *   *(Requires code analysis)* Classes annotated with `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`. Relationships defined using `@OneToMany`, `@ManyToOne`, `@ManyToMany`, `@OneToOne`. Lombok annotations (`@Data`, `@Getter`, `@Setter`, etc.) are likely used.
*   **View Templates:**
    *   Not applicable for the backend API itself. The view layer is handled by the separate Angular application in `src/main/UI`, whose built artifacts (`index.html`, JS, CSS) are served from `src/main/resources/static`.

### 4. Flow Analysis

*   **Request Lifecycle (Example: GET /room/reservation/v1/{roomId})**
    1.  HTTP GET request from Angular frontend (or other client) arrives.
    2.  Spring MVC DispatcherServlet routes to a matching `@GetMapping("/{roomId}")` method in `ReservationResource`.
    3.  Controller method extracts `{roomId}` using `@PathVariable`.
    4.  Controller calls a corresponding method in a Service class (e.g., `roomService.findById(roomId)` - injected via `@Autowired`).
    5.  Service method (likely marked `@Transactional(readOnly=true)`) calls the appropriate Repository method (e.g., `roomRepository.findById(roomId)`).
    6.  Spring Data JPA/Hibernate generates SQL, executes it against the H2 database.
    7.  Database returns data, Hibernate maps it to a `RoomEntity` object.
    8.  Repository returns `Optional<RoomEntity>` to the Service.
    9.  Service handles the `Optional`.
    10. Service returns the `RoomEntity` to the Controller.
    11. Controller wraps the `RoomEntity` in `ResponseEntity.ok()` (or handles not found cases with `ResponseEntity.notFound()`).
    12. Spring Boot (Jackson) serializes the `RoomEntity` to JSON.
    13. Response is sent back to the client.
*   **Data Flow:**
    *   Angular Client <-> REST Controller (HTTP Request/Response with JSON DTOs/Entities)
    *   REST Controller -> Service (Method calls with DTOs/IDs)
    *   Service -> Repository (Method calls with Entities/IDs)
    *   Repository -> H2 Database (SQL via JDBC)
    *   H2 Database -> Repository (ResultSets/Entities)
    *   Repository -> Service (Entities/Optionals)
    *   Service -> Convertor -> Service (Entity -> DTO mapping, if applicable)
    *   Service -> REST Controller (DTOs/Entities)
*   **Authentication/Authorization Flow:**
    *   *(Cannot determine without analyzing security configuration)* If Spring Security is added, it would intercept requests before they reach the controllers.
*   **Error Handling Mechanism:**
    *   *(Cannot determine without analyzing exception handling configuration)* Likely uses Spring Boot's default error handling or a custom `@ControllerAdvice` class to map exceptions to specific HTTP status codes and JSON error responses. Validation errors (`spring-boot-starter-validation`) would likely result in 400 Bad Request.

### 5. Dependency Mapping

```mermaid
graph TD
    subgraph "Client"
        AngularUI[Angular UI (Browser)]
    end

    subgraph "Presentation Layer (Backend)"
        RC[REST Controllers (rest/)]
        M[Models/DTOs (model/)]
        Conv[Convertors (convertor/)]
    end

    subgraph "Business Logic Layer"
        S[Services (in convertor/)]
    end

    subgraph "Data Access Layer"
        R[Repositories (repository/)]
        E[Entities (entity/)]
    end

    subgraph "Configuration & Bootstrap"
        Conf[Spring Config (config/)]
        Props[application.properties]
        Boot[H2Bootstrap]
        I18n[welcome*.properties]
        MsgSrc(Spring MessageSource)
    end

    subgraph "External Systems"
        H2[(H2 File DB)]
    end

    AngularUI -->|HTTP API Calls (JSON)| RC
    RC -->|Uses/Returns| M
    RC -->|Uses| Conv // For entity->response conversion
    RC -->|Calls| S
    S -->|Uses| R
    S -->|Uses/Returns| E
    Conv -->|Maps between| E
    Conv -->|Maps between| M
    R -->|Maps to/from| E
    R -->|Accesses| H2
    Conf -->|Configures| S
    Conf -->|Configures| R
    Conf -->|Configures| Conv
    Conf -->|Configures| H2
    Props -->|Provides Values| Conf
    Props -->|Provides Values| H2
    Boot -->|Populates| H2
    Boot -->|Uses| R  // Likely uses repositories to save initial data
    RC -->|Uses| MsgSrc // For localized messages
    MsgSrc -->|Reads| I18n


    %% Potential Issues / Notes:
    %% - Services mixed with Convertors in `convertor` package.
    %% - Direct calls from RC to R? (Check code - Yes, in ReservationResource)
    %% - Convertor logic complexity?
```

*   **Circular Dependencies:** Unlikely in this structure, but requires code analysis to confirm.
*   **Tight Coupling Points:**
    *   Controllers are coupled to Services (currently in `convertor` package).
    *   `ReservationResource` directly calls `RoomRepository` and `ReservationRepository` alongside services, bypassing the service layer for some operations.
    *   Services might become large if not properly scoped.
    *   Potential coupling between backend DTOs (`model/`) and the Angular frontend contract. Changes in one might require changes in the other.

### 6. Current Architecture Assessment

*   **Strengths:**
    *   Standard Spring Boot structure (though service layer location is non-standard).
    *   Clear separation of backend (Spring Boot) and frontend (Angular).
    *   Uses Spring Data JPA for simplified data access.
    *   Includes internationalization support (`.properties` files).
    *   Uses Maven for build management, including frontend build integration.
    *   Provides initial data seeding (`H2Bootstrap`).
*   **Architectural Weaknesses:**
    *   **Service Layer Location:** Business logic (Services) is currently located within the `convertor` package, which is confusing. A dedicated `service` package is standard practice.
    *   **Bypassing Service Layer:** `ReservationResource` directly accesses repositories for some operations, violating the layered architecture principle. All data access should ideally go through the service layer.
    *   Using `ddl-auto=update` is convenient for development but risky for production environments (potential data loss or unexpected schema changes). Production should use a migration tool (Flyway, Liquibase) or `validate`.
    *   Mixing H2 (active config) and MySQL (dependency present) suggests potential environment inconsistencies or incomplete setup for different deployment targets.
    *   No explicit security configuration identified. APIs might be unsecured.
*   **Maintenance Pain Points:**
    *   Locating business logic within the `convertor` package makes it harder to find and understand.
    *   Direct repository access from controllers makes the controllers harder to test and couples them directly to data access concerns.
    *   Managing the mapping logic in `convertor` could become complex if not using a library like MapStruct effectively.
    *   Keeping the Angular UI build integrated via Maven (`exec-maven-plugin`) can sometimes be brittle compared to separate build pipelines.
    *   Debugging issues related to the H2 file database state might be tricky.
*   **Security Vulnerabilities:**
    *   **High:** Likely lack of authentication/authorization via Spring Security. API endpoints are potentially open.
    *   Input validation might be missing in controllers/DTOs.
    *   Sensitive data exposure if Entities are returned directly from controllers instead of DTOs (as seen in `ReservationResource.getRoomById`).
    *   CSRF protection might be needed if the Angular app interacts via session cookies (less common for API + SPA).
    *   Dependencies might be outdated (Spring Boot 2.7.2 is old as of April 2025). Use `mvn versions:display-dependency-updates` to check.

---

## Refactoring Strategies: global-application-suite

Based on the documentation:

### 1. Structural Improvements

*   **Package Reorganization:**
    *   **Create Service Layer:** Create a distinct `service` package. Move `RoomService`, `RoomServiceImpl`, `ReservationService`, `ReservationServiceImpl` from `convertor` to the new `service` package. Update all imports accordingly.
    *   **Clarify `convertor` Package:** Ensure `convertor` only contains mapping logic (e.g., `*Converter` or `*Mapper` classes).
    *   **Feature vs. Layer:** The layer-based structure (`rest`, `service`, `repository`, `entity`, `model`, `convertor`) is reasonable.
*   **File/Folder Renaming:**
    *   Ensure consistent naming (`*Controller`, `*Service`, `*ServiceImpl`, `*Repository`, `*DTO`, `*Entity`, `*Mapper` or `*Converter`).
*   **Modularization Opportunities:**
    *   The current structure with separate `src/main/java` and `src/main/UI` is already a form of modularization. Keep backend and frontend code distinct.
    *   Consider extracting the `convertor` logic into its own module if it becomes very large or complex, or if using a library like MapStruct.

### 2. Code Quality Enhancements

*   **Enforce Layering:**
    *   Refactor `ReservationResource` to **only** call methods in the `service` layer (e.g., `RoomService`, `ReservationService`). Remove direct injections and calls to `RoomRepository` and `ReservationRepository` from the controller. Add corresponding methods to the services if needed.
    *   Ensure controllers return DTOs (`model` package) instead of Entities where appropriate (e.g., `getRoomById` should return a DTO mapped from the `RoomEntity`).
*   **Interface Abstraction:**
    *   Ensure controllers inject `Service` interfaces, not implementations (`ServiceImpl`).
    *   Ensure services inject `Repository` interfaces (already standard with Spring Data JPA).
    *   Use constructor injection consistently.
        ```java
        // Before (in Controller)
        // @Autowired
        // private RoomServiceImpl roomService;

        // After (in Controller)
        private final RoomService roomService;
        private final ReservationService reservationService;
        // ... other services/converters

        // @Autowired // Optional on constructor since Spring 4.3
        public ReservationResource(RoomService roomService, ReservationService reservationService, /*...other dependencies*/) {
            this.roomService = roomService;
            this.reservationService = reservationService;
            // ...
        }
        ```
*   **Design Patterns:**
    *   **MapStruct:** If `convertor` contains manual mapping code, consider using MapStruct library to generate mappers automatically via interfaces, reducing boilerplate and errors.
    *   **Strategy/Factory:** Look for complex conditional logic within services as candidates.
    *   **Builder:** Use Lombok's `@Builder` or manual Builder pattern for complex DTO/Entity creation.
*   **Testability Improvements:**
    *   Write unit tests for the `service` layer, mocking repository dependencies.
    *   Write integration tests for controllers (`@WebMvcTest`) mocking the service layer.
    *   Write integration tests for repositories (`@DataJpaTest`) using the embedded H2 database.
    *   Ensure `H2Bootstrap` logic is testable or can be disabled during tests.

### 3. Modernization Plan

*   **Dependency Updates:**
    *   **Crucial:** Update Spring Boot from 2.7.2 (released July 2022, OSS support ended Nov 2023) to the latest stable 3.x version (e.g., 3.2.x or 3.3.x as of April 2025). This requires **Java 17+** (already used).
    *   Update other dependencies (Spring Data, Hibernate, Lombok, Jackson, MySQL connector, etc.) accordingly. Use `mvn versions:display-dependency-updates`.
    *   Address security vulnerabilities reported by `mvn dependency-check:check` (requires OWASP plugin).
*   **Spring Boot 3.x Migration:**
    *   **Jakarta EE:** Perform the `javax.*` to `jakarta.*` package migration (essential for Boot 3.x). Spring Boot Migrator tool can assist.
    *   Review breaking changes documentation for Spring Boot 3.x.
    *   Adopt `Problem Details for HTTP APIs` for standardized REST error responses.
*   **Database Strategy:**
    *   **Clarify DB Usage:** Decide if MySQL is the target production database. If so, configure profiles (`application-dev.properties` for H2, `application-prod.properties` for MySQL).
    *   **Migrations:** Replace `ddl-auto=update` with `ddl-auto=validate` (or `none`) for production profiles. Introduce a database migration tool like **Flyway** or **Liquibase** to manage schema changes reliably. Add the corresponding Maven plugin.
*   **Improved Error Handling:**
    *   Implement a robust `@ControllerAdvice` with `@ExceptionHandler` methods mapping specific exceptions (custom, validation, security, etc.) to a standard `ErrorResponseDTO` and appropriate HTTP status codes.

### 4. Documentation Additions

*   **API Documentation (OpenAPI/Swagger):**
    *   **Highly Recommended:** Add `springdoc-openapi-starter-webmvc-ui` dependency.
    *   Annotate REST controllers and DTOs (`@Operation`, `@ApiResponse`, `@Schema`, etc.). This provides live, interactive documentation for the Angular frontend developers and other API consumers.
*   **Architectural Decision Records (ADRs):**
    *   Start documenting key decisions (e.g., "ADR-001: Create dedicated service package", "ADR-002: Choose H2 for Dev/Test, MySQL for Prod", "ADR-003: Implement Flyway for DB Migrations", "ADR-004: Upgrade to Spring Boot 3.x").
*   **Deployment Diagrams:**
    *   Create a diagram showing how the Spring Boot JAR and the Angular static files are deployed (e.g., single container, separate web server for UI, database instance). Refer to `deploying-on-cloud/Step-by-Step Guide to Deploying on Azure.docx` for existing context.

### Phased Implementation Plan & Effort Estimation

1.  **Phase 1: Structure & Stabilize (Medium Effort, High Impact)**
    *   **Create `service` package:** Move services, update imports. (Small)
    *   **Enforce Layering:** Refactor `ReservationResource` to use only services. Ensure DTOs are returned. (Medium)
    *   **Implement Security:** Add Spring Security starter and configure basic authentication/authorization. (Medium)
    *   **Database Strategy:** Configure profiles (dev/prod), replace `ddl-auto=update` with `validate` for prod, introduce Flyway/Liquibase. (Medium)
    *   **Error Handling:** Implement standard `@ControllerAdvice` and Error DTO. (Small)
    *   **Interface Injection:** Ensure controllers/services use interfaces and constructor injection. (Small)
    *   **Start ADRs:** Document the Service Layer, DB, and Security decisions. (Small - ongoing)
2.  **Phase 2: Modernize & Document (Medium-Large Effort)**
    *   **Upgrade Spring Boot 3.x:** Perform the upgrade, including Jakarta EE migration and dependency updates. Address breaking changes. (Medium-Large)
    *   **API Documentation:** Add `springdoc-openapi` and annotate APIs. (Medium)
3.  **Phase 3: Enhance & Refine (Medium Effort)**
    *   **Improve Test Coverage:** Add unit and integration tests, especially for services and controllers. (Medium)
    *   **Adopt MapStruct:** Refactor `convertor` package if manual mapping is complex. (Medium)
    *   **Review/Apply Design Patterns:** Address complex logic identified in services. (Small-Medium)
    *   **Create Deployment Diagram:** Document the deployment architecture. (Small)

**Prioritization:** Phase 1 (Structure & Stabilize) addresses key architectural flaws and security concerns. Phase 2 brings the application up-to-date and significantly improves developer experience (API docs). Phase 3 focuses on long-term maintainability and robustness. The Spring Boot upgrade (Phase 2) is a major step and should be planned carefully.
