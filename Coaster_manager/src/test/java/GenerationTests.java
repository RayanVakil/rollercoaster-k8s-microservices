/**
 * Initialized by Jean Aldoph II, This testing set will be
 * used to MOCK generate employees for the Management Service of the
 * theme park. These tests will only determine the functionality
 * of the tests, they DO NOT need to be called during service.
 */

//

import data.GenerationDAO;
import data.SQLDatabaseCustomerDAO;
import models.Customer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.junit.Test;
import java.time.LocalDateTime;

import java.io.*;



import utils.PostgresConnectionUtil;

@org.junit.Ignore
public class GenerationTests {
    private static final Logger logger = LoggerFactory.getLogger(GenerationTests.class);
//
//    ScriptEngineManager manager = new ScriptEngineManager();
//    ScriptEngine engine = manager.getEngineByName("JavaScript");
//    // read script file
//    engine.eval(Files.newBufferedReader(Paths.get("/EmployeeGeneration.js"), StandardCharsets.UTF_8)));
//
//    Invocable inv = (Invocable) engine;
// //call function from script file
// inv.invokeFunction("yourFunction", "param");

    @Test
    public void testGen()
    {

        GenerationDAO genDao = new GenerationDAO();
        try
        {
            genDao.makeAday(0);
        }
        catch (Exception e)
        {
            logger.error("Exception occurred", e);
        }
    }
    @Test
    public void testCall()
    {
        // Fake example transaction ID: 3YC00XQKNVMZ
        String url = "https://randomuser.me/api/";

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try
        {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.err.println("Method failed: " + response.statusCode());
            }

            String rezzy = response.body();
            String[] stack = rezzy.split("\".\"");
            int holder = 1;
            String item = "";
            String item1 = "";
            String item2 = "";
            for (String i : stack)
            {
                if (i.trim().equals("first"))
                {
                    item = (stack[holder]).toString();
                }
                else if (i.trim().contains("email"))
                {
                    item1 =(stack[holder]).toString();
                }
                else if (i.trim().contains("last"))
                {
                    item2 = (stack[holder]).split("\"")[0];
                }
                //System.out.println(holder-1 +"   "+i);
                holder++;

            }
            if (item2 == "") item2 ="Smith";
            if (item1 == "") item1 = "John";
            System.out.println("first Name: " + item);
            System.out.println("Last Name:  "+item2);
            System.out.println("email:  "+item1);

        } catch (Exception e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            logger.error("Exception occurred", e);
        }
    }

    @Test
    public void makeCustomers()
    {
        int i = 0;
        while (i++ < 1) new GenerationDAO().makeCustomer();
    }

    @Test
    public void makeEmployees()
    {
        int i = 0;
        while (i++ < 1) new GenerationDAO().makeEmployee();
    }

    @Test
    public void ldtPare()
    {
        LocalDateTime ldt;
    }

    @Test
    public void indciativeFoulTest()
    {
        new GenerationDAO().indicativeFoul();
    }

    @Test    public  void makeTicketsTest()
    {
        Customer c = new SQLDatabaseCustomerDAO(new PostgresConnectionUtil()).findById("supermario@nintendo.com");
        new GenerationDAO().makeTickets(c,1);
    }

}






