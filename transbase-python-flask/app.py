from flask import Flask, request, jsonify
from transbase import transbase

# Initialize the Flask app
app = Flask(__name__)

# Execute SQL statements to create the required database schema


def create_schema(connection):
    cursor = connection.cursor()
    cursor.execute("""drop table if exists cashbook;""")
    cursor.execute("""create table cashbook
(
	nr 	integer not null primary key auto_increment,
	date 	timestamp not null default currentdate,
	amount 	numeric(10,2) not null,
	comment varchar(*)
);""")
    # insert some test data
    cursor.execute(
        "insert into cashbook values (default, currentdate, -9.50, 'Lunch');"
    )
    cursor.execute(
        "insert into cashbook values (default, currentdate, 2789, 'Salary');"
    )
    cursor.close()


with app.app_context():
    # Connect to the Transbase database
    conn = transbase.connect(url="//localhost:2024/cashbook",
                             user="tbadmin", password="transbase")
    create_schema(conn)
    print("created database schema")


def to_record(row):
    if row is None:
        return None
    else:
        return {"id": row[0], "date": row[1], "amount": row[2], "comment": row[3]}

# Define routes for the REST service


@app.route('/cashbook', methods=['GET'])
def get_cashbook_entries():
    # Execute a query to fetch cashbook entries from the database
    query = "SELECT * FROM cashbook"
    cursor = conn.cursor()
    cursor.type_cast = True
    cursor.execute(query)

    # Fetch all rows and convert them to CashbookEntity objects
    rows = cursor.fetchall()
    cursor.close()

    return jsonify([to_record(row) for row in rows])


@app.route('/cashbook/<id>', methods=['GET'])
def get_cashbook_entry(id):
    # Execute a query to fetch the cashbook entry with the specified nr from the database
    query = "SELECT * FROM cashbook WHERE nr = ?"
    cursor = conn.cursor()
    cursor.type_cast = True
    cursor.execute(query, (id,))

    row = cursor.fetchone()

    cursor.close()
    if row is None:
        return jsonify({'message': 'Cashbook entry not found'})

    return jsonify(to_record(row))


@app.route('/cashbook', methods=['POST'])
def add_cashbook_entry():
    data = request.json

    # Insert the new cashbook entry into the database
    query = "INSERT INTO cashbook (nr, amount, date, comment) VALUES (default, :amount, :date, :comment)"
    cursor = conn.cursor()
    cursor.execute(query, data)
    cursor.close()

    return jsonify({'message': 'Cashbook entry added successfully'})


@app.route('/cashbook/<id>', methods=['PUT'])
def update_cashbook_entry(id):
    data = request.json
    data["id"] = id

    # Update the cashbook entry with the specified id
    query = "UPDATE cashbook SET amount = :amount, date = :date, comment = :comment WHERE nr = :id"
    cursor = conn.cursor()
    cursor.execute(query, data)

    return jsonify({'message': 'Cashbook entry updated successfully'})


@app.route('/cashbook/<id>', methods=['DELETE'])
def delete_cashbook_entry(id):
    # Delete the cashbook entry with the specified id
    query = "DELETE FROM cashbook WHERE nr = ?"
    cursor = conn.cursor()
    cursor.execute(query, (id,))
    cursor.close()

    return jsonify({'message': 'Cashbook entry deleted successfully'})


# Run the Flask app
if __name__ == '__main__':
    app.run(port=8080)
