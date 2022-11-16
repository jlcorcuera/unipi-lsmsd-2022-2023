
# Ecommerce Web Application

## Required software

For this session it is required to have installed:

- Java SDK 11. (*)
- Apache Maven 3.x version. (*)
- Apache Tomcat 9.x.
- An IDE (IntelliJ, Eclipse, Netbeans)

Also, do not forget to define the following environment variables:

- `M2_HOME` -> root directory of your Maven installation.
- `JDK_HOME` -> root directory of your JDK installation.
- Update the `PATH` environment variable by adding the `bin` directories of your JDK and Maven installations.

(*) You can avoid doing all these step manually by installing
[SDKMAN](https://sdkman.io/).

## Restoring a MySQL dump file

Run the following command in a terminal by using (do not forget to update/provide your MySQL credentials):

```sh
mysql -u <user> -p < unipi.sql
```

## Updating configuration parameters

In this version of this App, the configuration parameters are set in the following files:

- it.unipi.lsmsd.ecommerce.dao.base.BaseMySQLDAO
- it.unipi.lsmsd.ecommerce.dao.base.BaseRedisDAO

In a future version these parameters are going to be moved to an external configuration file.

## Running the application by using IntelliJ

Open the maven project in IntelliJ. After that, follow [these steps](https://www.jetbrains.com/idea/guide/tutorials/working-with-apache-tomcat/using-existing-application/) to run the application.
