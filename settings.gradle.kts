rootProject.name = "otusJava"
include("HW01-gradle")
include("HW02-generics")
include("HW03-annotations")
include("HW04-gc")
include("HW05-byteCodes")
include("HW06-solid")
include("HW07-patterns")
include("HW08-io")
include("HW09-jdbc")
include("HW10-jpql")
include("HW11-cache")
include("HW12-webServer")
include("HW13-di")
include("HW14-springDataJdbc")
include("HW15-executors")
include("HW16-concurrentCollections")
include("HW17-multiprocess")
include("HW18-webflux:client-service")
include("HW18-webflux:datastore-service")

pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings
    val protobufVer: String by settings
    val sonarlint: String by settings
    val spotless: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
    }
}