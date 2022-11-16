
# Ecommerce Web Application

## Implemented features:

### Functional requirements - Unregistered user:
- The system must allow unregistered users to browse products (a simplified view of each product will be available for the user).
- The system must allow an unregistered user to become a customer by registering his/her full name, address, phone number, email address and password.
- The system must allow an unregistered user to login as a registered user.


### Functional requirements - Customer:
- The system must allow a customer to browse products (a simplified view of each product will be available for the user).
- The system must allow a customer to find products (by  name) (a simplified view of each product will be available for the user).
- The system must allow a customer to add products to a cart while he/she is viewing a product.
- The system must allow a customer to see the content of the cart.
- The system must allow a customer to update his/her cart (to modify product quantity or to delete a product)
- The system must allow a customer to start the checkout process.
- The system must ask for a customer to insert an address and the billing method during the checkout process. 
- The system must allow a customer to cancel an in progress checkout process.
- The system must allow a registered user to display the result of the checkout process.
- The system must update the quantity of product in stock after each successful checkout process and clear the cart.

## Next release features:

### Functional requirements - Unregistered user:
- The system must allow unregistered users to display the information of a selected product. This information must include the type, name, description, images, price, other attributes defined by each type and reviews.

### Functional requirements - Customer:
- The system must allow a customer to change his/her account information including their password.
- The system must allow a customer to display the information of a selected product. This information must include the type, name, description, images, price, other attributes defined by each type and reviews.
- The system must allow a customer to see the progress of his/her order.
- The system must allow a customer to see all his/her orders.

### Functional requirements - Company manager:

- All requirements.

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
