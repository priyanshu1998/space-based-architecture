package dev.priyanshu.model;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillId implements Serializable {

  private UUID id;

  public static BillId generate() {
    return new BillId(UUID.randomUUID());
  }
}
