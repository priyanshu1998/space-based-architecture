package dev.priyanshu.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

class EventType {
  public static final String BILLING_EVENT = "billing-event";
  public static final String PRODUCT_EVENT = "product-event";
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = BillingEvent.class, name = EventType.BILLING_EVENT),
  @JsonSubTypes.Type(value = ProductEvent.class, name = EventType.PRODUCT_EVENT),
})
public abstract class Event {}
