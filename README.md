# RepBook API

### Overview

[RepBook](https://repbook.app/api) is a full-stack workout-tracking app. This API is made with Spring Boot and PostgreSQL.

Also, I used AWS RDS for PostgreSQL, ElastiCache for Redis, Parameter Store for secrets, and ECS to deploy the API to cloud. I used Cloudflare to manage DNS records.


### Developing

Make sure to have a PostgreSQL server running and a Redis server running.

Then, fill in credentials in <code>/resources/application.properties</code>.

Run <code>main()</code> in <code>RepbookApiApplication.java</code> to start the API server.


### Building an Image
```
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=repbook-api -DskipTests
```

---

#### <ins>[RepBook UI Repo](https://github.com/jakethellama/repbook-ui)</ins>
