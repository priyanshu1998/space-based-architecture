package dev.priyanshu.cache.operator;

public enum InventoryOperation {
  SELL, // affect quantity
  BUY, // affect quantity
  DISCOUNT, // affect price
  INFLATE, // affect price
}
