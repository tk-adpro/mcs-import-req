package id.ac.ui.cs.advprog.eshop.mcsimportreq.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String imageUrl;
    private Double price;
    private String storeUrl;
    private String currency;

    public Request() {
    }

    public Request(String productName, String imageUrl, Double price, String storeUrl, String currency) {
        setProductName(productName);
        setImageUrl(imageUrl);
        setPrice(price);
        setStoreUrl(storeUrl);
        setCurrency(currency);
    }

    public void setProductName(String productName) {
        if (!StringUtils.hasText(productName)) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.productName = productName;
    }

    public void setImageUrl(String imageUrl) {
        if (!StringUtils.hasText(imageUrl)) {
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
        if (!StringUtils.hasText(storeUrl)) {
            throw new IllegalArgumentException("Store URL cannot be empty");
        }
        this.storeUrl = storeUrl;
    }

    public void setCurrency(String currency) {
        if (!StringUtils.hasText(currency)) {
            throw new IllegalArgumentException("Currency cannot be empty");
        }
        this.currency = currency;
    }
}
