			propertyChangeListener = propertyChangeEvent -> {
				String property = propertyChangeEvent.getProperty();
				if (IAction.ENABLED.equals(property) || IAction.CHECKED.equals(property)
						|| IHandlerAttributes.ATTRIBUTE_HANDLED.equals(property)) {

					Map previousAttributeValuesByName = attributeValuesByName;
					attributeValuesByName = getAttributeValuesByNameFromAction();
					if (!attributeValuesByName.equals(previousAttributeValuesByName)) {
						fireHandlerChanged(new HandlerEvent(ActionHandler.this, true, previousAttributeValuesByName));
					}
				}
			};
		}
