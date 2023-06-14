# Transbase Python Flask

### Requirements

You need a running transbase database for this example (//localhost:2024/cashbook)
with user `tbadmin` and password `transbase`.
Transbase can be started with the included [docker-compose.yml](../docker-compose.yml) script:

`docker-compose up`

then create a new cashbook database with

- `docker exec -it transbase tbi`
- `connect //localhost:2024/admin tbadmin transbase`
- `create database cashbook set encryption=none;`

Install required libraries
`pip install -r requirements.txt`

Start the server with
`python app.py`

A cashbook rest api is available at
`http://localhost:8080/cashbook`

### Reference Documentation

For further reference, please consider the following sections:

- [Transbase Getting Started](https://www.transaction.de/fileadmin/public/transbase/8.4/docu/getstart.xhtml)
- [Transbase nodejs](https://github.com/TransactionSoftwareGmbH/transbase-nodejs)
- [Express](http://expressjs.com/)
