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

    public void setImageUrl(String imageUrl) {
        if (StringUtils.isEmpty(imageUrl)) {
            throw new IllegalArgumentException("Image URL cannot be empty");
        }
        this.imageUrl = imageUrl;
    }

    public void setPrice(Double price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    public void setStoreUrl(String storeUrl) {
        if (StringUtils.isEmpty(storeUrl)) {
            throw new IllegalArgumentException("Store URL cannot be empty");
        }
        this.storeUrl = storeUrl;
    }

    public void setCurrency(String currency) {
        if (StringUtils.isEmpty(currency)) {
            throw new IllegalArgumentException("Currency cannot be empty");
        }
        this.currency = currency;
    }
}
