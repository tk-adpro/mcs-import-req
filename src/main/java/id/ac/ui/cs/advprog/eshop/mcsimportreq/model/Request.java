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
        if (StringUtils.isEmpty(productName) || productName == null) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.productName = productName;
    }

    public void setImageUrl(String imageUrl) {
        if (StringUtils.isEmpty(imageUrl) || imageUrl == null) {
            throw new IllegalArgumentException("Image URL cannot be empty");
        }
        this.imageUrl = imageUrl;
    }

    public void setPrice(Double price) {
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price cannot be negative or zero");
        }
        this.price = price;
    }

    public void setStoreUrl(String storeUrl) {
        if (StringUtils.isEmpty(storeUrl) || storeUrl == null) {
            throw new IllegalArgumentException("Store URL cannot be empty");
        }
        this.storeUrl = storeUrl;
    }

    public void setCurrency(String currency) {
        if (StringUtils.isEmpty(currency) || currency == null) {
            throw new IllegalArgumentException("Currency cannot be empty");
        }
        this.currency = currency;
    }
}
