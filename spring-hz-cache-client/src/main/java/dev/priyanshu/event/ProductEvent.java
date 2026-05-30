package dev.priyanshu.event;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(EventType.PRODUCT_EVENT)
public class ProductEvent extends Event {}
