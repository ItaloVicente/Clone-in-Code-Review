			if (!attributeValuesByName
			        .equals(previousAttributeValuesByName)) {
				fireHandlerChanged(new HandlerEvent(
			            ActionHandler.this, true,
			            previousAttributeValuesByName));
			}
               }
            };
        }
