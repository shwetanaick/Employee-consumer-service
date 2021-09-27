# Employee-consumer-service

This service is designed to consume kafka records to a local kafka topic "test_topic".

**Prerequisites:**

Local Instances of zookeeper and kafka should be up and running.

**Service:**

The Producer service exposes 3 endpoints : store, update and read.
Store and Update accepts JSON payloads of an Employee class and also a header "FileType" which can be "CSV" or "XML".
Read can be performed irrespective of fileType, using a param "id" which is a combination of name_dob.
All the 3 endpoints publish record to Kafka, and the consumer service consumes records and performs the corresponding operation.


Consumer Service consumes each record verifies the operation.

If operation is STORE then it checks the corresponding FileType (CSV/XML) and searches if the record is already inserted by comparing the id, if not it will append the new record based on the file type.

If the operation is UPDATE, it picks up the existing record based on the id and updates and inserts the same in the corresponding file (XML/CSV).

If the end point is READ, it picks up the id from the param and checks in both CSV and XML, if record is found, it send the response back.




**Topic Name ** :  "test_topic".
READ: http://localhost:9002/file/read/csv2_20-08-2111

Only Read operation is done over HTTP.
