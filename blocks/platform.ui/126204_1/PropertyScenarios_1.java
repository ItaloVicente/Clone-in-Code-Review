		IValidator validator = value -> {
			String stringValue = (String) value;
			try {
				double doubleValue = new Double(stringValue).doubleValue();
				if (doubleValue < 0.0) {
					return ValidationStatus.error(cannotBeNegativeMessage);
				}
				return Status.OK_STATUS;
			} catch (NumberFormatException ex) {
				return ValidationStatus.error(mustBeCurrencyMessage);
			}
		};
