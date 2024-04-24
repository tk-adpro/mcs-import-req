package id.ac.ui.cs.advprog.eshop.mcsimportreq.model;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class Request {
    private Long id;
    private String productName;
    private String imageUrl;
    private Double price;
    private String storeUrl;
    private String currency;

    public Request(String productName, String imageUrl, Double price, String storeUrl, String currency) {
        setProductName(productName);
        setImageUrl(imageUrl);
        setPrice(price);
        setStoreUrl(storeUrl);
        setCurrency(currency);
    }

    public void setProductName(String productName) {
        if (StringUtils.isEmpty(productName)) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.productName = productName;
    }

    public void setPrice(Double price) {
        if (price != null && price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }
}
