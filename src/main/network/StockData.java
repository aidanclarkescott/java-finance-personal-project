package network;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;

public class StockData {

    // EFFECTS: gets stock data from api website based on given stock code and returns price data
    public double formatApiQuery(String stockCode) throws IOException {
        String query = "https://financialmodelingprep.com/api/v3/stock/real-time-price/" + stockCode;
        String jsonFile = readUrl(query);
        return parseJsonData(jsonFile);
    }

    // EFFECTS: takes in JSON file and extracts price from it.
    public double parseJsonData(String query) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(query);

            JSONObject jsonObject = (JSONObject) obj;

            double price = (double) jsonObject.get("price");

            return price;

        } catch (ParseException e) {
            System.out.println("ParseException");
        }

        return 0;
    }

    // EFFECTS: reads text from api query and formats it into one string.
    private String readUrl(String urlString) throws IOException {
        BufferedReader br = null;
        try {
            URL url = new URL(urlString);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            return sb.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

}
