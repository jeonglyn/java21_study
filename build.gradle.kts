plugins {
    id("java")
}

group = "com.study"         // 프로젝트 그룹 (패키지 최상위 이름과 맞추는 관례)
version = "1.0-SNAPSHOT"    // 버전 (SNAPSHOT = 아직 개발 중인 버전이라는 의미)

java {
    // Java 21 명시적 설정
    // sourceCompatibility = 소스 코드 작성 버전
    sourceCompatibility = JavaVersion.VERSION_21

    // 컴파일 결과물(.class 파일)을 Java 21 JVM에서 실행 가능하게 생성
    // targetCompatibility = 실행 환경 버전
    targetCompatibility = JavaVersion.VERSION_21

    // toolchain = 실제로 사용할 JDK를 명시적으로 고정
    // sourceCompatibility만 쓰면 환경에 따라 다른 JDK가 쓰일 수 있음
    // toolchain까지 써야 "무조건 JDK 21로 컴파일해" 가 보장됨
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    // mavenCentral = 전 세계 Java 라이브러리 중앙 저장소
    mavenCentral()
}

dependencies {
    //테스트
    // JUnit 5 테스트 프레임워크 설정
    // BOM(Bill of Materials) = 여러 JUnit 모듈 버전을 한 번에 통일해서 관리
    testImplementation(platform("org.junit:junit-bom:5.10.2"))

    // testImplementation = 테스트 코드에서만 사용하는 의존성
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // 롬복
    // Lombok = 반복적인 코드(getter/setter/생성자 등)를 어노테이션으로 자동 생성
    // compileOnly = 컴파일 시에만 필요, 실행 시엔 필요 없음
    compileOnly("org.projectlombok:lombok:1.18.32")

    // annotationProcessor = 컴파일 시 어노테이션을 실제 코드로 변환하는 역할
    annotationProcessor("org.projectlombok:lombok:1.18.32")

}

tasks.withType<JavaCompile> {
    //Virtual Thread, Record 등 모든 java21 기능 활성화
    // --enable-preview: Java 21의 Preview 기능까지 허용
    //   Preview 기능 = 정식 출시 전 미리 써볼 수 있는 기능들
    //   (일부 실험적 Pattern Matching, String Template 등)
    // -source 21: 소스 코드 버전이 21임을 컴파일러에 명시
    //   --enable-preview는 반드시 -source 버전과 함께 써야 동작함
    options.compilerArgs.addAll(listOf("--enable-preview", "-source", "21"))
}

tasks.withType<Test> {
    // JUnit 5(Jupiter) 방식으로 테스트를 실행하겠다는 선언
    // JUnit 4 방식과 다르기 때문에 명시 필요
    useJUnitPlatform()

    // 테스트 실행 시에도 Preview 기능 허용
    // 컴파일할 때 --enable-preview로 만든 코드를 테스트할 때도 동일하게 적용
    jvmArgs("--enable-preview")
}

tasks.withType<JavaExec> {
    // main() 메서드 직접 실행 시에도 Preview 기능 허용
    // IntelliJ에서 ▶ 버튼으로 실행할 때 여기를 탐
    jvmArgs("--enable-preview")
}