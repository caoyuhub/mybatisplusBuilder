plugins {
  id("java")
  id("org.jetbrains.intellij") version "1.5.2"
}

group = "www.cy27.cn"
version = "1.0.0"

repositories {
  maven {
    url = uri("https://maven.aliyun.com/nexus/content/repositories/central/")
  }
  mavenCentral() // 默认的 Maven 中央仓库
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
  version.set("2022.1.4")
//  version.set("2024.3.3")
  type.set("IU") // Target IDE Platform

  plugins.set(listOf(/* Plugin Dependencies */))
}

dependencies{
  implementation("org.springframework.boot:spring-boot-starter-freemarker:2.7.18")
  implementation("cn.hutool:hutool-all:5.8.36")
  implementation("com.baomidou:mybatis-plus-generator:3.5.10.1")
  implementation("com.baomidou:mybatis-plus-boot-starter:3.5.10.1")
  implementation("org.slf4j:slf4j-api:2.1.0-alpha1")


//  DB2驱动
  implementation("com.ibm.db2:jcc:12.1.0.0")
//  达梦驱动
  implementation("com.dameng:DmJdbcDriver18:8.1.3.140")
//  mysql驱动
  implementation("mysql:mysql-connector-java:8.0.33")
//  oracle驱动
  implementation("com.oracle.database.jdbc:ojdbc11:23.7.0.25.01")
//  postgresql驱动
  implementation("org.postgresql:postgresql:42.7.5")
//  sqlserver驱动
  implementation("com.microsoft.sqlserver:mssql-jdbc:12.9.0.jre11-preview")
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
    options.encoding = "UTF-8"
  }

  patchPluginXml {
    sinceBuild.set("200")
    untilBuild.set("250.*")
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}
