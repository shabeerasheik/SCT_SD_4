import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;

public class WebScraper {

    public static void main(String[] args) {

        String url = "https://books.toscrape.com/";

        try {

            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .get();

            Elements products = document.select("article.product_pod");

            FileWriter writer = new FileWriter("products.csv");

            writer.write("Product Name,Price,Rating,Availability,Product URL\n");

            for (Element product : products) {

                String name = product.select("h3 a").attr("title");

                String price = product.select(".price_color").text();

                String rating = product.select("p.star-rating")
                        .attr("class")
                        .replace("star-rating", "")
                        .trim();

                String availability = product.select(".instock")
                        .text()
                        .trim();

                String productUrl = "https://books.toscrape.com/catalogue/"
                        + product.select("h3 a").attr("href").replace("../", "");

                writer.write("\"" + name + "\"," +
             "\"" + price + "\"," +
             "\"" + rating + "\"," +
             "\"" + availability + "\"," +
             "\"" + productUrl + "\"\n");

                System.out.println("--------------------------------");
                System.out.println("Name : " + name);
                System.out.println("Price : " + price);
                System.out.println("Rating : " + rating);
                System.out.println("Availability : " + availability);
                System.out.println("URL : " + productUrl);
            }

            writer.close();

            System.out.println("\nData successfully saved to products.csv");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

