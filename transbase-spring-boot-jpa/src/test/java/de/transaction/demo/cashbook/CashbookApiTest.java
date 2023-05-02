package de.transaction.demo.cashbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CashbookApiTest
{

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CashbookRepository repository;

    @AfterEach
    void cleanUp()
    {
        repository.deleteAll();
    }

    @Test
    void testGetCashbooks()
    {
        createRecord(1200, "Salary");
        createRecord(-12, "Lunch");

        ResponseEntity<CashbookRecord[]> response =
            restTemplate.getForEntity("/cashbooks", CashbookRecord[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
    }

    @Test
    void testGetCashbook()
    {
        createRecord(-12, "Lunch");

        ResponseEntity<CashbookRecord[]> list =
            restTemplate.getForEntity("/cashbooks", CashbookRecord[].class);
        assertNotNull(list.getBody());

        ResponseEntity<CashbookRecord> details =
            restTemplate.getForEntity("/cashbooks/" + list.getBody()[0].id(), CashbookRecord.class);

        assertEquals(HttpStatus.OK, details.getStatusCode());
        assertNotNull(details.getBody());
        assertEquals("Lunch", details.getBody().comment());
    }

    @Test
    void testCreateCashbook()
    {
        CashbookRecord cashbook = new CashbookRecord(null, Instant.now(), 100, "Lunch");

        ResponseEntity<Integer> response = restTemplate.postForEntity("/cashbooks", cashbook, Integer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testDeleteCashbook()
    {
        CashbookRecord cashbook = new CashbookRecord(null, Instant.now(), 100, "Lunch");

        ResponseEntity<Integer> created = restTemplate.postForEntity("/cashbooks", cashbook, Integer.class);

        restTemplate.delete("/cashbooks/" + created.getBody());

        ResponseEntity<String> getResponse =
            restTemplate.getForEntity("/cashbooks/" + created.getBody(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    private void createRecord(float amount, String comment)
    {
        repository.save(new Cashbook(new CashbookRecord(null, Instant.now(), amount, comment)));
    }
}
