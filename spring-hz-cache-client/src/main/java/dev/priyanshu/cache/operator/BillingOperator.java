package dev.priyanshu.cache.operator;

public class BillingOperator {

  public BillingAction payNowAction =
      (order) -> {
        return new BillingInfo();
      };

  public BillingAction payLaterAction =
      (order) -> {
        return new BillingInfo();
      };

  public BillingAction rentAction =
      (order) -> {
        return new BillingInfo();
      };
}
