package id.ac.ui.cs.advprog.eshop.mcsimportreq.model;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;
import org.springframework.util.StringUtils;

@Data
@Entity
public class Request {
    @Id
    private UUID id;
    private String productName;
    private String imageUrl;
    private Double price;
    private String storeUrl;
    private String currency;

    // No-arg constructor
    public Request() {
    }

    private Request(Builder builder) {
        this.id = builder.id;
        this.productName = builder.productName;
        this.imageUrl = builder.imageUrl;
        this.price = builder.price;
        this.storeUrl = builder.storeUrl;
        this.currency = builder.currency;
    }

    public static class Builder {
        private UUID id;
        private String productName;
        private String imageUrl;
        private Double price;
        private String storeUrl;
        private String currency;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setProductName(String productName) {
            if (StringUtils.isEmpty(productName) || productName == null) {
                throw new IllegalArgumentException("Product name cannot be empty");
            }
            this.productName = productName;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            if (StringUtils.isEmpty(imageUrl) || imageUrl == null) {
                throw new IllegalArgumentException("Image URL cannot be empty");
            }
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setPrice(Double price) {
            if (price == null || price <= 0) {
                throw new IllegalArgumentException("Price cannot be negative or zero");
            }
            this.price = price;
            return this;
        }

        public Builder setStoreUrl(String storeUrl) {
            if (StringUtils.isEmpty(storeUrl) || storeUrl == null) {
                throw new IllegalArgumentException("Store URL cannot be empty");
            }
            this.storeUrl = storeUrl;
            return this;
        }

        public Builder setCurrency(String currency) {
            if (StringUtils.isEmpty(currency) || currency == null) {
                throw new IllegalArgumentException("Currency cannot be empty");
            }
            this.currency = currency;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}