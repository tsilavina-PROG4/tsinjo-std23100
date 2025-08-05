package com.tsinjo.app.endpoint.event.consumer.model;

import com.tsinjo.app.PojaGenerated;
import com.tsinjo.app.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
