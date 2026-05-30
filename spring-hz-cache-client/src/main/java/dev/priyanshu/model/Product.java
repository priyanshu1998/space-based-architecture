package dev.priyanshu.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

  private ProductId productId;
  private String name;
  private String description;
  private String category;
  private BigDecimal price;
  private int stockQuantity;
}
