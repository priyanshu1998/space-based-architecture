package dev.priyanshu.event;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(EventType.BILLING_EVENT)
public class BillingEvent extends Event {}
