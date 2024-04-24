package id.ac.ui.cs.advprog.eshop.mcsimportreq.model;

import lombok.Data;

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
        this.productName = productName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(Double price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
