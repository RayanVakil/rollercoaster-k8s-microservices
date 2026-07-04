package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import dao.AttractionDAO;
import models.Attraction;
import utils.ConnectionUtils;
import utils.PostgresConnectionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Initialized by Jean Aldoph II
 * The Servlet for all API calls involving Attractions that can be seen by the Customer.
 * This Servlet only implements one Method, doGet
 * This is the case because our customers should have no updating or posting privileges to our DB
 *
 *version 0.0: Jean D Aldoph II
 */



public class AttractionsServlet extends HttpServlet
{
    private static final Logger logger = LoggerFactory.getLogger(AttractionsServlet.class);
    /**
     * This Method is the only method implemented by Customer for attractions.
     * This method takes an httpRequest and calls to the DAO for our Attractions.
     * The customer Attraction DAO can only be used to view our Current Attractions, to alleviate confusion,
     * Or allow customers to view a Specific attraction.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        {
            //System.out.println("");
            AttractionDAO repo = new AttractionDAO(new PostgresConnectionUtil());
            if (req.getHeader("find").equals("all"))
            {
                String json = null;
                List<Attraction> all = repo.findAll();
                Map<String, List> options = new LinkedHashMap<>();
                options.put("attraction", all);
                //System.out.println(options);
                json = new Gson().toJson(options);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
            }
            else if (!(req.getHeader("find") == null))
            {
                try
                {
                    String json = null;
                    Attraction temp = repo.findById(Integer.parseInt(req.getHeader("find")));
                    Map<String, Attraction> options = new LinkedHashMap<>();
                    options.put("attraction", temp);
                    //System.out.println(options);
                    json = new Gson().toJson(options);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(json);
                }
                catch (NumberFormatException e)
                {
                    logger.error("Exception occurred", e);
                resp.setStatus(400);
                }
                catch (IOException e)
                {
                    logger.error("Exception occurred", e);
                resp.setStatus(400);
                }
            }
        }
    }

}
