const express = require("express");
const { Transbase } = require("@transaction/transbase-nodejs");

// Create an instance of Express
const app = express();

// Middleware to parse JSON bodies
app.use(express.json());

// Create a Transbase connection
const transbase = new Transbase({
  url: "//localhost:2024/cashbook",
  user: "tbadmin",
  password: "transbase",
});

// GET /cashbook
app.get("/cashbook", async (req, res) => {
  try {
    const result = transbase.query("SELECT * FROM cashbook");
    res.json(result.toArray());
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
});

// GET /cashbook/{id}
app.get("/cashbook/:id", async (req, res) => {
  try {
    const { id } = req.params;

    const result = transbase.query("SELECT * FROM cashbook where nr = :id", {
      id,
    });

    const record = result.next();

    if (!result.hasNext()) {
      res.status(404).json({ error: `Cashbook with id: ${id} not found` });
    }

    res.json(record);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
});

// POST /cashbook
app.post("/cashbook", async (req, res) => {
  const { amount, comment, date } = req.body;

  if (!amount || !comment || !date) {
    return res.status(400).json({ error: "Missing required fields" });
  }

  try {
    transbase.query(
      "INSERT INTO cashbook (nr, amount, comment, date) VALUES (default, ?, ?, ?)",
      [amount, comment, date]
    );
    res.status(201).json({ message: "Cashbook entry created successfully" });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
});

// PUT /cashbook/:id
app.put("/cashbook/:id", async (req, res) => {
  try {
    const { id } = req.params;
    const { amount, comment, date } = req.body;

    if (!amount || !comment || !date) {
      return res.status(400).json({ error: "Missing required fields" });
    }

    transbase.query(
      "UPDATE cashbook SET amount = ?, comment = ?, date = ? WHERE nr = ?",
      [amount, comment, date, id]
    );

    res.json({ message: "Cashbook entry updated successfully" });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
});

// DELETE /cashbook/:id
app.delete("/cashbook/:id", async (req, res) => {
  try {
    const { id } = req.params;

    transbase.query("DELETE FROM cashbook WHERE nr = :id", { id });

    res.json({ message: "Cashbook entry deleted successfully" });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
});

// Start the server
const port = 8080;
const server = app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});

// Handle server teardown
process.on("SIGINT", async () => {
  try {
    // Close the Transbase connection
    transbase.close();
    // Close the Express server
    server.close(() => {
      process.exit(0);
    });
  } catch (error) {
    console.error("Error during server shutdown:", error);
    process.exit(1);
  }
});
