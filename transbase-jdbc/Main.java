import java.util.Date;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws ClassNotFoundException
    {
        Class.forName("transbase.jdbc.Driver");

        CashbookService cashbookService = new CashbookService();

        // drop and create a new cashbook table
        cashbookService.dropAndCreateSchema();

        // Add a cashbook entry
        Cashbook entry = new Cashbook(1, 100.0, new Date(), "First entry");
        cashbookService.addCashbook(entry);

        // Get all cashbook entries
        List<Cashbook> entries = cashbookService.getAllCashbookEntries();
        entries.stream()
            .map(it -> entry.nr() + ", " + entry.amount() + ", " + entry.date() + ", " + entry.comment())
            .forEach(System.out::println);

        // Update a cashbook entry
        Cashbook entryToUpdate = entries.get(0);
        cashbookService.updateCashbook(new Cashbook(entryToUpdate.nr(), 200.0, new Date(), "Updated entry"));

        // Delete a cashbook entry
        int entryNrToDelete = entries.get(0).nr();
        cashbookService.deleteCashbook(entryNrToDelete);

        cashbookService.closeConnection();
    }
}
