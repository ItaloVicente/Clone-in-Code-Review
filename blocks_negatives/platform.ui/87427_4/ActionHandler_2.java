                        Map previousAttributeValuesByName = attributeValuesByName;
                        attributeValuesByName = getAttributeValuesByNameFromAction();
                        if (!attributeValuesByName
                                .equals(previousAttributeValuesByName)) {
							fireHandlerChanged(new HandlerEvent(
                                    ActionHandler.this, true,
                                    previousAttributeValuesByName));
						}
                    }
                }
