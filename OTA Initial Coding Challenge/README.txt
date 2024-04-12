Hi OTA,

I have finished the coding challenge using the Spring Boot application. I don't believe you need a setup guide to run this, but I'll insert here the libraries that I have used.

I have used this libraries for the JSON read and write.
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.0</version> <!-- Use the latest version available -->
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-annotations</artifactId>
    <version>2.17.0</version> <!-- Use the same version as jackson-databind -->
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.17.0</version> <!-- Use the same version as jackson-databind -->
</dependency>

Note about the application: Insert, Put, and Delete are working well, but the application needs to be rerun for the JSON changes to be applied.

Thank you for this awesome challenge. I hope I can proceed with the application. Thank you.