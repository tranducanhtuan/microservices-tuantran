# microservices-tuantran

## 1. Techstack:
- **_DB:_**
  + Current: H2 DB (but saved in file so it's still keep your data even service is restarted): help fast build up project & testing.
  + Next: Postgres or MariaDB will be considered.
- **_Backend:_**
  + Java 8.
  + Spring, Spring Boot, Spring Cloud Nexflix (Eureka, Zuul Proxy, Ribbon, Feign Client..): robust, fast setup & distribute system, huge community.
  + Zipkin: tracing request/response & latency across services.
  + Kafka message broker: fast, high reliability, easy to scale out, can replay message if needed -> suitable for sending SMS.
  + Testing: Junit/Mockito/PowerMock.
- **_Frontend:_** 
  + We can use SPA like ReactJS, AngularJS -> high performance & good user experience.

## 2. System Architecture: 
- **_Backend:_**  
![service_architect-Backend](https://user-images.githubusercontent.com/45473913/115419253-627fd800-a224-11eb-9dd8-bb4a4aacbc3a.png)
  
  
- **_Frontend:_**  
![service_architect-Frontend](https://user-images.githubusercontent.com/45473913/115420796-a32c2100-a225-11eb-8f3b-e49921006ef6.png)

- **_Infrastructures:_**
- **_Interservice communication:_**
- **_Front end 30s explanation:_**


## 3. Software development principles, patterns & practices:
- Object oriented design with SOLID.
- Microservices principles: event driven, loose coupling, single responsibility.
- REST principles: 
- Layering architect:

## 4. Database tables design:
![DB_tables_design](https://user-images.githubusercontent.com/45473913/115374995-8e389900-a1f7-11eb-909b-3d8ae41337c4.png)  

## 5. Package structure:
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

## 6. Setup local environment to run project:
### 6.1. Preparation:  
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

### 6.3. Testing:
#### 6.3.1. Postman:
  Import file **_Bank.postman_collection.json_** from main path of git repo into your Postman, 
  it will lead you to URL of gateway service which is the only one we publish to outside.  
  ![image](https://user-images.githubusercontent.com/45473913/115378003-57b04d80-a1fa-11eb-806c-8901019861fe.png)
  
#### 6.3.2. Test introdution:
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
  
#### 6.3.3. Main testcases execution:
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
  
#### 6.3.4. Tracing request/response & latency across services:
  Please access this link: [http://localhost:8081/zipkin/](http://localhost:8081/zipkin/)  
  ![image](https://user-images.githubusercontent.com/45473913/115384694-87af1f00-a201-11eb-8028-63e83fa4018a.png)
  
#### 6.3.5. Verify in H2 database:
  Please access this link without password: [http://localhost:8083/h2](http://localhost:8083/h2)  
  ![image](https://user-images.githubusercontent.com/45473913/115384894-bf1dcb80-a201-11eb-8939-815159d84bcd.png)
  ![image](https://user-images.githubusercontent.com/45473913/115385078-e83e5c00-a201-11eb-94d6-1cc112741d8b.png)


## 7. TODO:
- Create Auth service to use with User table.
- Authenticate using real JWT token.
- Centralize Exception handling.
- Standardize response content format.
- Customize JPA naming stragegy to use more convenient.
- Create 1 common repository which store common things usually be used from many services.
- Create enterprise DB like Postgres/MariaDB...
- Logging & centralize logs using Elastic stack/Splunk.
- Monitoring system using Prothemios & Grafana.
- And some more...
  
## Thank you & best regards.
Tuan Tran (Andy)  
Phone: my tokens
