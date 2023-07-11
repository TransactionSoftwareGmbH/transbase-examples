# Transbase Node.js Graphql

### Requirements

You need a running transbase database for this example (//localhost:2024/cashbook)
with user `tbadmin` and password `transbase`.
Transbase can be started with the included [docker-compose.yml](../docker-compose.yml) script:

`docker-compose up`

then create a new cashbook database with

- `docker exec -it transbase tbi`
- `connect //localhost:2024/admin tbadmin transbase`
- `create database cashbook set encryption=none;`

Start the application with
`npm start`

A cashbook graphql api (apollo explorer) is available at
`http://localhost:8080`

Here are some example gql queries to execute:

query all cashbook entries:

```graphql
query Cashbooks {
  cashbooks {
    nr
    amount
    date
    comment
  }
}
```

add a new cashbook entry:

```graphql
mutation {
  addCashbook(amount: 100, date: "2023-07-11", comment: "New cashbook entry")
}
```

update an existing cashbook entry:

```graphql
mutation {
  updateCashbook(
    nr: 1
    amount: 200
    date: "2023-07-12"
    comment: "Updated cashbook entry"
  )
}
```

delete a cashbook entry:

```graphql
mutation {
  deleteCashbook(nr: 1)
}
```

### Reference Documentation

For further reference, please consider the following sections:

- [Transbase Getting Started](https://www.transaction.de/fileadmin/public/transbase/8.4/docu/getstart.xhtml)
- [Transbase nodejs](https://github.com/TransactionSoftwareGmbH/transbase-nodejs)
- [Graphql](https://graphql.org/graphql-js/)
