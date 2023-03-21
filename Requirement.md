## Problem Statement

1. Bank ABC want to provide a new feature on its website. The feature is to purchase
prepaid data for a SIM card by getting a voucher code from a 3rd party. Anyone can
purchase the prepaid data on the website without login.
2. The 3rd parties provide an API for the client to call via HTTP(s) protocol to get the
voucher code. The API always returns a voucher code after 3 to 120 seconds, it
depends on the network traffic at that time.
3. The bank wants to build a new service(s) to integrate with those 3rd parties. But it
expects that the API will return a voucher code or a message that says the request is
being processed within 30 seconds.
4. If the web application receives the voucher code, it will show on the website for
customers to use. In case that the code can't be returned in-time, the customer can
receive it via SMS later.
5. The customer can check all the voucher codes that they have purchased by phone
number on the website, but it needs to be secured.
6. Assume that the payment has been done before the website call the services to get
the voucher code.


## Expected Outputs

1. Example implementation for some operations of at least two of the backend web
services. The example implementation must demonstrate inter-service
communication between these services.
2. Entity-relationship diagram for the database and solution diagrams for the
components, infrastructure design if any
3. For candidates applying for the Principal Engineer or Engineering Manager
position, it's important to have your solution design covers not only the Java
backend web services but also all other components of the application as well
including the infrastructure
4. Readme file includes:
5. A brief explanation for the software development principles, patterns & practices
being applied
6. A brief explanation for the code folder structure and the key Java libraries and
frameworks being used
7. All the required steps in order to get the application run on a local computer
8. Full CURL commands to verify the APIs (include full request endpoint, HTTP
Headers and request payload if any)
