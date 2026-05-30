package dev.priyanshu.cache.operator;

import dev.priyanshu.model.Order;

@FunctionalInterface
public interface BillingAction {
  BillingInfo bill(Order order);
}
