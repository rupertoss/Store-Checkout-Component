# Store Checkout Component

## Overview

Store Checkout Component is market checkout component.
It's written as REST API platform.

## Project Details

Project uses Spring Boot and dependencies are controlled by Maven.
Data is stored in MySQL database and project uses Hibernate to communicate.

So far project supports actions only from customer perspective. That means that customer can get Item details, can perform CRUD operation on Cart and have no option to modify Promotions.

There are 3 repositories: Items, Carts and Promotions.
Customer can create new Cart, perform various operations on it like adding items to it, changing items quantity and more. After all can go to "checkout" where with promotion code can lower cart's value.

### Tests

Project contains Integrate tests and Unit tests for service and controller layers.
Controller unit tests are written with usage of Mockito.

### HTTP requests

All API requests are made by sending a HTTP request using one of the following methods,depending on the action being taken. JSON is the format of body request.

Item REST:
* GET

Cart REST:
* GET
* CREATE
* UPDATE
* DELETE

### HTTP responses

All actions performed on API returns required body with proper HTTP status.
JSON is the format of body response.

## Languages

This project is authored in Java.

## Installation

Clone the project to your host machine.

## Future implementations

Add administrator view perspective to perform more operations like adding items, promotions and more.

Spring Security
Hibernate HQL queries
OneToMany/ManyToMany etc. mapping
Logger
Actuator