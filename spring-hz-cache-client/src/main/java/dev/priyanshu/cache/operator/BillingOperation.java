package dev.priyanshu.cache.operator;

public enum BillingOperation {
  PAY_NOW, // Update Inflow and balance
  PAY_LATER, // Update outflow and balance
  RENT, // Update outflow
}
