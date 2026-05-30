package dev.priyanshu.model;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductId implements Serializable {

  private UUID id;

  public static ProductId generate() {
    return new ProductId(UUID.randomUUID());
  }
}
