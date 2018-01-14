## Prerequisites

Please follow these instructions to start developing

1. Download & Install JDK1.8 latest.
2. Download & Install latest Eclipse IDE for Java EE Developers (https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/marsr).
3. Download & Install PostgreSql DB (https://www.postgresql.org/download/windows/) - remember to set db password as "C@rpe_d!em".
4. Download & Install Redis (https://redis.io/download).
5. Checkout source code and import in Eclipse (import existing maven project).
6. In Eclipse : Project>Clean.
7. Go to "bin" folder at the Postgres installation path, open cmd and login using command : "psql -U postgres -W". Enter password "C@rpe_d!em".
Run command "create database scholarum".
6. Run service/src/main/java/com.scholarum.service.Application.java as Java Application. Application will start @ port 7070.
7. To change port, change server.port in service/src/main/resource/application.properties.