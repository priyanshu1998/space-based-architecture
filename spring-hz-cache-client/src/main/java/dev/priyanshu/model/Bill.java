package dev.priyanshu.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill implements Serializable {

  private BillId billId;
  private String customerId;
  private String customerName;
  private List<Product> products;
  private BigDecimal totalAmount;
  private BillStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public enum BillStatus {
    PENDING,
    PAID,
    CANCELLED,
    REFUNDED
  }
}
