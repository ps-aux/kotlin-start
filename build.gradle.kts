buildscript {
	val springBootVersion = "1.5.1.RELEASE"
	val kotlinVersion = "1.0.6"
	extra["kotlinVersion"] = kotlinVersion

	repositories {
		mavenCentral()
	}

	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
		classpath("org.jetbrains.kotlin:kotlin-noarg:$kotlinVersion")
		classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
	}
}

apply {
	plugin("kotlin")
	plugin("kotlin-spring")
	plugin("kotlin-jpa")
	plugin("org.springframework.boot")
}

version = "0.0.1-SNAPSHOT"

configure<JavaPluginConvention> {
	setSourceCompatibility(1.8)
	setTargetCompatibility(1.8)
}

repositories {
	mavenCentral()
}

val kotlinVersion = extra["kotlinVersion"] as String
val seleniumVersion = "3.2.0"

dependencies {
    compileOnly("org.projectlombok:lombok:1.16.12")
	compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    compile("org.seleniumhq.selenium:selenium-support:$seleniumVersion")
    compile("org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion")
	compile("com.google.guava:guava:21.0")

	compile("com.codeborne:phantomjsdriver:1.3.0")

    compile("org.postgresql:postgresql:9.4-1201-jdbc41")

	compile("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
	compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
	compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.8.4")

	testCompile("org.springframework.boot:spring-boot-starter-test")
}

