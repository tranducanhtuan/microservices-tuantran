# microservices-tuantran

## Table of contents
- [Requirement](#requirement)
- [1. Techstack](#1-techstack)
  * [1.1. DB](#11-db)
  * [1.2. Backend](#12-backend)
  * [1.3. Frontend](#13-frontend)
- [2. System Architecture](#2-system-architecture)
  * [2.1. Backend](#21-backend)
  * [2.2. Frontend](#22-frontend)
  * [2.3. Infrastructures](#23-infrastructures)
  * [2.4. In-Service layering](#24-in-service-layering)
  * [2.5. Inter-Services communication](#25-inter-services-communication)
- [3. Software development principles or patterns or practices](#3-software-development-principles-or-patterns-or-practices)
- [4. Database tables design](#4-database-tables-design)
- [5. Package structure](#5-package-structure)
- [6. Setup local environment to run project](#6-setup-local-environment-to-run-project)
  * [6.1. Preparation](#61-preparation)
  * [6.2. Start Services](#62-start-services)
  * [6.2. Code coverage](#62-code-coverage)
- [7. Testing](#7-testing)
  * [7.1. Postman](#71-postman)
  * [7.2. Test introdution](#72-test-introdution)
  * [7.3. Main testcases execution](#73-main-testcases-execution)
  * [7.4. Tracing request and latency across services](#74-tracing-request-and-latency-across-services)
  * [7.5. Verify in H2 database](#75-verify-in-h2-database)
- [8. TODO](#8-todo)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>


## Requirement
- See [Requirement.md](https://github.com/tranducanhtuan/microservices-tuantran/blob/main/Requirement.md)
## 1. Techstack
### 1.1. DB
- Current: H2 DB (but saved in file so it's still keep your data even service is restarted): help fast build up project & testing.
- Next: Postgres or MariaDB will be considered.
### 1.2. Backend
- Java 8.
- Spring, Spring Boot, Spring Cloud Nexflix (Eureka, Zuul Proxy, Ribbon, Feign Client..): robust, fast setup & distribute system, huge community.
- Zipkin: tracing request/response & latency across services.
- Kafka message broker: fast, high reliability, easy to scale out, can replay message if needed -> suitable for sending SMS.
- Testing: Junit/Mockito/PowerMock.
### 1.3. Frontend
- We can use SPA like ReactJS, AngularJS -> high performance & good user experience.

## 2. System Architecture 
### 2.1. Backend
![service_architect-Backend](https://user-images.githubusercontent.com/45473913/115650732-04eaa900-a354-11eb-8d41-dd3872fa86ec.png)

### 2.2. Frontend  
![service_architect-Frontend](https://user-images.githubusercontent.com/45473913/115580498-56158100-a2f1-11eb-979e-98d5beb70cfc.png)

### 2.3. Infrastructures
- **_Backend:_**  
  ![service_architect-AWS backend infrastructure](https://user-images.githubusercontent.com/45473913/115653856-28185700-a35a-11eb-8be4-60706d37afb1.png)
  
- **_Frontend:_**  
![service_architect-AWS Frontend Infrastructure](https://user-images.githubusercontent.com/45473913/115653872-2fd7fb80-a35a-11eb-9125-be36eb24379e.png)
  
### 2.4. In-Service layering  
  ![image](https://user-images.githubusercontent.com/45473913/115496389-b5dd3f00-a293-11eb-9826-4616dfe9c115.png)  
  ![image](https://user-images.githubusercontent.com/45473913/115495928-d5c03300-a292-11eb-8ae7-673ee26055c3.png)  

### 2.5. Inter-Services communication
- REST: 
    ![image](https://user-images.githubusercontent.com/45473913/115572840-78f06700-a2ea-11eb-825d-c77ce76f8d45.png)  
    ![image](https://user-images.githubusercontent.com/45473913/115573048-a1786100-a2ea-11eb-8bad-98d735bd7536.png)  
 
- Event:  
    ![image](https://user-images.githubusercontent.com/45473913/115573325-d97fa400-a2ea-11eb-9e1b-809be808135b.png)  
    ![image](https://user-images.githubusercontent.com/45473913/115574038-793d3200-a2eb-11eb-9861-d2f8031c5fb7.png)  
    ![image](https://user-images.githubusercontent.com/45473913/115574524-e8b32180-a2eb-11eb-98ae-990dbab8ff19.png)  

## 3. Software development principles or patterns or practices
- Object oriented design with SOLID.
- Microservices principles: loose coupling, single responsibility, event driven, fault-tolerant, you build it you own it.
- RESTful principles: http method rule, endpoint url rule, stateless.
- Layering architect: as above "In-Service layering" section.
- Profiling environment: local, dev, prod.

## 4. Database tables design
![DB_tables_design](https://user-images.githubusercontent.com/45473913/115374995-8e389900-a1f7-11eb-909b-3d8ae41337c4.png)  

## 5. Package structure
**_Depth 1:_** organization country (au)  
---**_Depth 2:_** organization classification (co)  
------**_Depth 3:_** Organization name (nab)  
---------**_Depth 4:_** System name (poc)  
------------**_Depth 5:_** Subsystem name (microservice unit or library) (order)  
---------------**_Depth 6:_** Domain name (voucher)  
------------------**_Depth 7:_** Layer name  
---------------------------**_controller:_** public REST API (VoucherController.java)  
---------------------------**_dto:_** data transfer object, exchange data between client vs service (VoucherDto.java)  
---------------------------**_service:_** biz logic service interface (VoucherService.java)  
---------------------------**_service/impl:_** biz logic service implementation (VoucherServiceImpl.java)  
---------------------------**_repository:_** Data storage in resource layers (VoucherRepository.java)  
---------------------------**_entity:_** persistent object mapping with database table (VoucherJpo.java)  
---------------------------**_proxy:_**  
---------------------------**_proxy/rest:_** interservice/thirdparty communication  
---------------------------**_proxy/event:_** event processing.  

## 6. Setup local environment to run project
### 6.1. Preparation
- **_Step 1:_** checkout this git repository.  
- **_Step 2:_** import all maven project into your IDE (i'm using IntelliJ)  
![image](https://user-images.githubusercontent.com/45473913/115363351-963f0b80-a1ec-11eb-8b0a-712497fbed81.png)  
- **_Step 3:_** make sure on your IDE, all imported projects are setup to use Java8 & Maven.  
- **_Step 4:_** open the below **_bootstrap.xml_** file & change **_searchLocations_** path to your checked-out config repo  
![image](https://user-images.githubusercontent.com/45473913/115364217-5c223980-a1ed-11eb-8374-c6bfc35d26d8.png)  
- **_Step 5:_** **_maven clean build_** for each project.  
- **_Step 6:_** pull docker Kafka & Zookeeper combined image from Docker Hub  
```docker pull johnnypark/kafka-zookeeper```  

### 6.2. Start Services
- Suggest following below order.  
- Please wait for each service well-done start before doing the next services.  
- Config service uses **_native_** profile.  
 
| Order  | Service name          | Profile | Port | Start Command |
| ------ |:----------------------|:-------:|:---------|:-------------|
| 1      | Kafka Zookeeper | n/a | 9092 2181 | ```docker run --name nab-kafka-zookeeper -p 2181:2181 -p 9092:9092 -e ADVERTISED_HOST=127.0.0.1 johnnypark/kafka-zookeeper```|
| 2      | Voucher Thirdparty | local | 7000 | |
| 3      | Zipkin | local | 8081 | |
| 4      | Config | **native** | 8888 | |
| 5      | Eureka | local | 8761 | |
| 6      | Gateway | local | 8082 | |
| 7      | Order | local | 8083 | |
| 8      | SMS | local | 8084 | |

### 6.2. Code coverage
![image](https://user-images.githubusercontent.com/45473913/115571318-15b20500-a2e9-11eb-9245-bb07f22893f5.png)
![image](https://user-images.githubusercontent.com/45473913/115571556-4eea7500-a2e9-11eb-9861-b40ae95515f3.png)

## 7. Testing
### 7.1. Postman
  Import file **_Bank.postman_collection.json_** from main path of git repo into your Postman, 
  it will lead you to URL of gateway service which is the only one we publish to outside.  
  ![image](https://user-images.githubusercontent.com/45473913/115378003-57b04d80-a1fa-11eb-806c-8901019861fe.png)
  
### 7.2. Test introdution
- **_User authentication:_**  
Currently i have not yet created a seperated auth service, instead of that i do hardcode below values to use as JWT tokens for users  
(in the future we can add user phone number into JWT token payload):  
  + User 1 token: **_0909492488_**  
  + User 2 token: **_0971668930_**  
Put it as a Header Authorization parameter if you want to access secured API:  
![image](https://user-images.githubusercontent.com/45473913/115378507-d0afa500-a1fa-11eb-86b1-c0aac3c473b4.png)
  
- **_Voucher thirdparty:_**  
In order to simulate happy case (response <=30 seconds) and unhappy case (response > 30 seconds), i do mockup to wait randomly between 3 & 40 seconds, so you may see slow response when testing, that's what we expected.  
- **_SMS service:_**  
I currently do send SMS message into service console so please check there.  
  
### 7.3. Main testcases execution
- Create voucher using phone number, within 3 seconds & without token:  
  ![image](https://user-images.githubusercontent.com/45473913/115379847-220c6400-a1fc-11eb-8a3d-937a01d64bb0.png)
  ![image](https://user-images.githubusercontent.com/45473913/115380076-63047880-a1fc-11eb-8fae-ee9a7e546df3.png)
  ![image](https://user-images.githubusercontent.com/45473913/115380428-c0002e80-a1fc-11eb-9324-9bc768e6af46.png)
  ![image](https://user-images.githubusercontent.com/45473913/115381929-4a955d80-a1fe-11eb-94b8-ca68e2c305c1.png)
  
- Create voucher using phone number, within 40 seconds & without token:  
  ![image](https://user-images.githubusercontent.com/45473913/115381137-79f79a80-a1fd-11eb-8b72-a0f621498d1c.png)
  ![image](https://user-images.githubusercontent.com/45473913/115381643-f5594c00-a1fd-11eb-8556-069959bd1f08.png)
  
- Find my vouchers without token:  
  ![image](https://user-images.githubusercontent.com/45473913/115382088-787aa200-a1fe-11eb-9c3b-af857d85db11.png)
  
- Find my vouchers with token:  
  ![image](https://user-images.githubusercontent.com/45473913/115382447-e7f09180-a1fe-11eb-8b9f-b570662afdaa.png)
  
- Can not see vouchers of other users:
  ![image](https://user-images.githubusercontent.com/45473913/115382652-21290180-a1ff-11eb-895f-9edba6abb1a4.png)
  
### 7.4. Tracing request and latency across services
  Please access this link: [http://localhost:8081/zipkin/](http://localhost:8081/zipkin/)  
  ![image](https://user-images.githubusercontent.com/45473913/115384694-87af1f00-a201-11eb-8028-63e83fa4018a.png)
  
### 7.5. Verify in H2 database
  Please access this link without password: [http://localhost:8083/h2](http://localhost:8083/h2)  
  ![image](https://user-images.githubusercontent.com/45473913/115384894-bf1dcb80-a201-11eb-8939-815159d84bcd.png)
  ![image](https://user-images.githubusercontent.com/45473913/115385078-e83e5c00-a201-11eb-94d6-1cc112741d8b.png)


## 8. TODO
- Create Auth service to use with User table.
- Authenticate using real JWT token.
- Centralize Exception handling.
- Standardize response content format.
- Customize JPA naming stragegy to use more convenient.
- Create 1 common repository which store common things usually be used from many services.
- Add Jacoco code coverage report.
- Create enterprise DB like Postgres/MariaDB...
- Logging & centralize logs using Elastic stack/Splunk.
- Monitoring system using Prothemios & Grafana.
- And some more...
  
## Thank you & best regards.
Tuan Tran (Andy)  
Phone No: my token
