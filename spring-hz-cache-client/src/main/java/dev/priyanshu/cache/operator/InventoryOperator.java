package dev.priyanshu.cache.operator;

import dev.priyanshu.model.ProductId;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class InventoryOperator {

  private record Update(ProductId id, int quantity) {}

  public Map<String, String> bulkBuy(ProductId id, int quantity) {
    return Map.of();
  }

  public Map<String, String> bulkSell(ProductId id, int quantity) {
    return Map.of();
  }
}
