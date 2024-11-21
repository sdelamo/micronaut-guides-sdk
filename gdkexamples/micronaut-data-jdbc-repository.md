---
layout: gdk-module
link_title: Create and Connect a Micronaut Application to a MySQL Database
title: Create and Connect a Micronaut Application to a MySQL Database
permalink: /gdk-modules/database/micronaut-data-jdbc-repository/
toc_group: gdk-modules-jdbc-database
---

# Create and Connect a Micronaut Application to a MySQL Database

This guide describes how to create a database application using the Graal Development Kit for Micronaut (GDK). The application presents REST endpoints and stores data in a MySQL database using Micronaut<sup>&reg;</sup> Data.

{% include_relative includes/mn-data-summary.md %}

### Prerequisites

* JDK 17 or higher. See [Setting up Your Desktop](/gdk/get-started/setting-up-desktop/).
* A Docker-API compatible container runtime such as [Rancher Desktop](https://docs.rancherdesktop.io/getting-started/installation/) or [Docker](https://www.docker.io/gettingstarted/) installed to run MySQL and to run tests using [Testcontainers](https://www.testcontainers.org).
* The GDK CLI. See [Setting up Your Desktop](/gdk/get-started/setting-up-desktop/). (Optional.)

Follow the steps below to create the application from scratch. However, you can also download the completed example:

{% include download-zips.md module="database" provider="independent" %}

{% include dev_env_note.md %}

> Note: If you use IntelliJ IDEA, [enable annotation processing](/gdk/resources/img/annotationprocessorsintellij.png).

<blockquote>
  <p>Windows platform: The GDK guides are compatible with Gradle only. Maven support is coming soon.</p>
</blockquote>

## 1. Create the Application

Create an application using the GDK Launcher.

1. Open the [GDK Launcher in advanced mode](/gdk/launcher/?advanced=true).

2. Create a new project using the following selections.
    * **Project Type**: _Application_ (Default)
    * **Project Name**: _db-demo_
    * **Base Package**: _com.example_ (Default)
    * **Clouds**: _None_
    * **Language**: _Java_ (Default)
    * **Build Tool**: _Gradle (Groovy)_ or _Maven_
    * **Test Framework**: _JUnit_ (Default)
    * **Java Version**: _17_ (Default)
    * **Micronaut Version**: (Default)
    * **Cloud Services**: _Database_
    * **Features**: _GraalVM Native Image_ (Default)
    * **Sample Code**: _Yes_ (Default)

3. Switch to the **Cloud Services** tab and make sure the **Database** service is selected. Deselect the other services. The Database service bundles all necessary features for a Micronaut database application: **Micronaut Data JDBC**, **Hikari JDBC Connection Pool**, **MySQL driver** and default config, **Flyway Database Migration**.

4. Switch to **Selected** tab to verify the selection. You should see **Database** and the **GraalVM Native Image** packaging feature (selected by default) selected.

5. Click **Generate Project**, then click **Download Zip**. The GDK Launcher creates an application with the default package `com.example` in a directory named _db-demo_. The application ZIP file will be downloaded to your default downloads directory. Unzip it, open it in your code editor, and proceed to the next steps.

Alternatively, use the [GDK CLI](/gdk/get-started/using-gdk-cli/) as follows:

<div id="tabs-doc2">
  <ul>
    <li class="tabs-gradle"><a href="#gradle">Gradle</a></li>
    <li class="tabs-maven"><a href="#maven">Maven</a></li>
  </ul>
  <div id="gradle">
    <pre><code class="language-bash">gdk create-app com.example.db-demo \
    --services=database \
    --features=graalvm \
    --build=gradle \
    --jdk=17 \
    --lang=java</code></pre>
  </div>
  <div id="maven">
    <pre><code class="language-bash">gdk create-app com.example.db-demo \
    --services=database \
    --features=graalvm \
    --build=maven \
    --jdk=17 \
    --lang=java</code></pre>
  </div>
</div>

If you enable sample code generation, the GDK Launcher creates the main controller, repository interface, entity, service classes, and tests for you.
In the _micronaut-cli.yml_ file you can find all features packaged with the application:

```yml
features: [app-name, data, data-jdbc, flyway, gdk-bom, gdk-database, gdk-license, gdk-platform-independent, graalvm, http-client-test, java, java-application, jdbc-hikari, junit, logback, maven, maven-enforcer-plugin, micronaut-aot, mysql, netty-server, properties, readme, serialization-jackson, shade, test-resources, validation]
```

Let's examine the project more closely.

### 1.1. Configure Datasource

The default datasource is defined in the _src/main/resources/application.properties_ file:

```
datasources.default.dialect=MYSQL
datasources.default.db-type=mysql
datasources.default.driver-class-name=com.mysql.cj.jdbc.Driver
```

If you deploy to, for example, Oracle MySQL Database, substitute the `driver-class-name` value with the Oracle Database Server (see [Create and Connect a Micronaut Application to the Oracle Cloud Infrastructure MySQL HeatWave Service](micronaut-mysql-database-oci.md)).

### 1.2. Database Migration with Flyway

The GDK Launcher included [Flyway](http://www.flywaydb.org) for database migrations. It uses the [Micronaut integration with Flyway](https://micronaut-projects.github.io/micronaut-flyway/latest/guide/) that automates schema changes, significantly simplifies schema management tasks, such as migrating, rolling back, and reproducing in multiple environments.
The GDK Launcher enables Flyway in the _src/main/resources/application.properties_ file and configures it to perform migrations on the default datasources.

```
flyway.datasources.default.enabled=true
```

> Note: Flyway migrations are not compatible with the default automatic schema generation that is configured in _src/main/resources/application.properties_. If `schema-generate` is active, it will conflict with Flyway. So edit _src/main/resources/application.properties_ and either delete the `datasources.default.schema-generate=CREATE_DROP` line or change that line to `datasources.default.schema-generate=NONE` to ensure that only Flyway manages your schema.

Configuring multiple datasources is as simple as enabling Flyway for each one. You can also specify directories that will be used for migrating each datasource. For more information, see [Micronaut integration with Flyway](https://micronaut-projects.github.io/micronaut-flyway/latest/guide/).

Flyway migration is automatically triggered before your application starts. Flyway reads migration file(s) in the _src/main/resources/db/migration/_ directory. The migration file with the database schema, _src/main/resources/db/migration/V1\_\_schema.sql_, was also created for you by the GDK Launcher.

```sql
DROP TABLE IF EXISTS genre;

CREATE TABLE genre (
   id    BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name  VARCHAR(255) NOT NULL UNIQUE
);
```

During application startup, Flyway runs the commands in the SQL file and creates the schema needed for the application.

### 1.3. Domain Entity

{% include_relative includes/genre.md parentDir="" %}

You could use a [subset of supported JPA annotations](https://micronaut-projects.github.io/micronaut-data/latest/guide/#sqlAnnotations) instead by including the following `compileOnly` scoped dependency: `jakarta.persistence:jakarta.persistence-api`.

### 1.4. Repository Interface

{% include_relative includes/genre-repository.md parentDir="" dialect="MYSQL" %}


### 1.5. Controller

{% include_relative includes/genre-controller.md parentDir="" %}

### 1.6. Service

{% include_relative includes/genre-service.md parentDir="" %}


### 1.7. Tests

{% include_relative includes/genre-controller-test.md cloud="" %}


## 2. Test the Application

To run the tests, use the following command:
<div id="tabs-doc3">
<ul>
    <li class="tabs-gradle"><a href="#gradle">Gradle</a></li>
    <li class="tabs-maven"><a href="#maven">Maven</a></li>
</ul>
<div id="gradle">
    <pre><code class="language-bash">./gradlew test</code></pre>
</div>
<div id="maven">
    <pre><code class="language-bash">./mvnw test</code></pre>
</div>
</div>

When the application is started locally&mdash;either under test or by running the application&mdash;resolution of the datasources URL is detected, the Test Resources service will start a local MySQL container, and inject the properties required to use this as the datasources.

For more information, see the [JDBC section of the Test Resources documentation](https://micronaut-projects.github.io/micronaut-test-resources/latest/guide/#modules-databases-jdbc).

## 3. Generate a Native Executable Using GraalVM

{% include guides/graalvm_prereq.md %}

To generate a native executable, use the following command:

<div id="tabs-doc4">
  <ul>
      <li class="tabs-gradle"><a href="#gradle">Gradle</a></li>
      <li class="tabs-maven"><a href="#maven">Maven</a></li>
  </ul>
  <div id="gradle">
      <pre><code class="language-bash">./gradlew nativeCompile</code></pre>
      <p>The native executable, <em>db-demo</em>, is created in the <em>build/native/nativeCompile/</em> directory.</p>
  </div>
  <div id="maven">
      <pre><code class="language-bash">./mvnw package -Dpackaging=native-image</code></pre>
      <p>The native executable, <em>db-demo</em>, is created in the <em>target/</em> directory.</p>
  </div>
</div>

Before running this native executable, you need to start and then connect to a MySQL database.

## 4. Connect to a MySQL Database

Start and connect to an existing database. Then define the database driver URL, username, and password via environment variables.

Use the following command to run a MySQL container:
```bash
docker run -it --rm \
    -p 3306:3306 \
    -e MYSQL_DATABASE=db \
    -e MYSQL_USER=sherlock \
    -e MYSQL_PASSWORD=elementary \
    -e MYSQL_ALLOW_EMPTY_PASSWORD=true \
    mysql:8
```

> Note: If you are using macOS on Apple Silicon (M1, M1 Pro), Docker might fail to pull a container image for `mysql:8`. In that case, substitute  with `mysql:oracle`.

Define the database driver URL, username, and password via environment variables:

<div class="tabs-doc" data-name="system">
    <div data-value="linux" data-label="Linux &amp; macOS">
      <pre><code class="language-bash">export DATASOURCES_DEFAULT_URL=jdbc:mysql://localhost:3306/db
export DATASOURCES_DEFAULT_USERNAME=sherlock
export DATASOURCES_DEFAULT_PASSWORD=elementary
</code></pre>
   </div>
   <div data-value="windows" data-label="Windows">
      <pre><code class="language-bash">set DATASOURCES_DEFAULT_URL=jdbc:mysql://localhost:3306/db
set DATASOURCES_DEFAULT_USERNAME=sherlock
set DATASOURCES_DEFAULT_PASSWORD=elementary
</code></pre>
   </div>
   <div data-value="windows-powershell" data-label="Windows PowerShell">
      <pre><code class="language-bash">$ENV:DATASOURCES_DEFAULT_URL = "jdbc:mysql://localhost:3306/db"
$ENV:DATASOURCES_DEFAULT_USERNAME = "sherlock"
$ENV:DATASOURCES_DEFAULT_PASSWORD = "elementary"</code></pre>
   </div>
</div>

The Micronaut framework populates the properties `datasources.default.url`, `datasources.default.username` and `datasources.default.password` with those environment variables' values. Learn more about [JDBC Connection Pools](https://micronaut-projects.github.io/micronaut-sql/latest/guide/#jdbc-connection-pools).

## 5. Run the Application

Run the application from the native executable which starts the application on port 8080:

<div id="tabs-doc5">
    <ul>
        <li class="tabs-gradle"><a href="#gradle">Gradle</a></li>
        <li class="tabs-maven"><a href="#maven">Maven</a></li>
    </ul>
    <div id="gradle">
        <pre><code class="language-bash">build/native/nativeCompile/db-demo</code></pre>
    </div>
    <div id="maven">
        <pre><code class="language-bash">target/db-demo</code></pre>
    </div>
</div>

Save one genre and your `genre` database table will now contain an entry:
```bash
curl -X "POST" "http://localhost:8080/genres" \
        -H 'Content-Type: application/json; charset=utf-8' \
        -d '{ "name": "music" }'
```

Access the `genres` endpoint exposed by the application:
```bash
curl localhost:8080/genres/list
```

When you run the application, Micronaut Test Resources do not start a MySQL container because you have provided values for `datasources.default.*` properties

### Summary

This guide demonstrated how to use the GDK to create a Micronaut database application that stores data in a MySQL database. You also learned how to package and run this application as a native executable.

### Related Documentation

* [Micronaut Data](https://micronaut-projects.github.io/micronaut-data/latest/guide/)
* [Native Build Tools](https://graalvm.github.io/native-build-tools/latest/index.html)
* [GraalVM Native Image](https://www.graalvm.org/)
