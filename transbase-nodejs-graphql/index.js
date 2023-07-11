const { ApolloServer, gql } = require("apollo-server");
const { Transbase } = require("@transaction/transbase-nodejs");

// Connect to the Transbase database
const connection = new Transbase({
  url: "//localhost:2024/cashbook",
  user: "tbadmin",
  password: "transbase",
});

// GraphQL schema definition
const typeDefs = gql`
  type Cashbook {
    nr: ID!
    amount: Float!
    date: String!
    comment: String
  }

  type Query {
    cashbook(nr: ID!): Cashbook
    cashbooks: [Cashbook]
  }

  type Mutation {
    addCashbook(amount: Float!, date: String!, comment: String!): Int
    updateCashbook(
      nr: ID!
      amount: Float!
      date: String!
      comment: String!
    ): Int
    deleteCashbook(nr: ID!): Int
  }
`;

// GraphQL resolver functions
const resolvers = {
  Query: {
    async cashbook(_, { nr }) {
      const result = connection.query("SELECT * FROM cashbook WHERE nr = ?", [
        nr,
      ]);
      return result.next();
    },
    async cashbooks() {
      const result = connection.query("SELECT * FROM cashbook");
      return result.toArray();
    },
  },
  Mutation: {
    async addCashbook(_, { amount, date, comment }) {
      return connection.query(
        "INSERT INTO cashbook (amount, date, comment) VALUES (?, ?, ?)",
        [amount, date, comment]
      );
    },
    async updateCashbook(_, { nr, amount, date, comment }) {
      return connection.query(
        `UPDATE cashbook SET amount = ?, date = ?, comment = ? WHERE nr = ?`,
        [amount, date, comment, nr]
      );
    },
    async deleteCashbook(_, { nr }) {
      return connection.query(`DELETE FROM cashbook WHERE nr = ${nr}`);
    },
  },
};

// Create Apollo Server and start listening
const server = new ApolloServer({ typeDefs, resolvers });
server.listen({ port: 8080 }).then(({ url }) => {
  console.log(`Server running at ${url}`);
});
