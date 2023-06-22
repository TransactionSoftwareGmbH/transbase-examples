import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CashbookService
{
    private final Connection connection;

    public CashbookService()
    {
        // Initialize the database connection
        try
        {
            connection = DriverManager.getConnection("jdbc:transbase://localhost:2024/cashbook", "tbadmin",
                "transbase");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void dropAndCreateSchema()
    {
        try (Statement stmt = connection.createStatement())
        {
            stmt.execute("drop table if exists cashbook;");
            stmt.execute(
                "create table cashbook (nr integer not null primary key auto_increment, date timestamp not null default currentdate, amount numeric(10,2) not null, comment varchar(*));");
            stmt.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void addCashbook(Cashbook entry)
    {
        try (PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO cashbook (nr, amount, date, comment) VALUES (default, ?, ?, ?)");)
        {
            statement.setDouble(1, entry.amount());
            statement.setDate(2, new java.sql.Date(entry.date().getTime()));
            statement.setString(3, entry.comment());

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public List<Cashbook> getAllCashbookEntries()
    {
        List<Cashbook> entries = new ArrayList<>();

        try (Statement statement = connection.createStatement();)
        {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cashbook");

            while (resultSet.next())
            {
                int nr = resultSet.getInt("nr");
                double amount = resultSet.getDouble("amount");
                Date date = resultSet.getDate("date");
                String comment = resultSet.getString("comment");

                Cashbook entry = new Cashbook(nr, amount, date, comment);
                entries.add(entry);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return entries;
    }

    public void updateCashbook(Cashbook entry)
    {
        try (PreparedStatement statement = connection.prepareStatement(
            "UPDATE cashbook SET amount = ?, date = ?, comment = ? WHERE nr = ?");)
        {
            statement.setDouble(1, entry.amount());
            statement.setDate(2, new java.sql.Date(entry.date().getTime()));
            statement.setString(3, entry.comment());
            statement.setInt(4, entry.nr());

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void deleteCashbook(int nr)
    {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM cashbook WHERE nr = ?");)
        {
            statement.setInt(1, nr);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection()
    {
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
