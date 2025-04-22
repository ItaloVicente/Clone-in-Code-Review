		IValidator validator = value -> {
			String stringValue = (String) value;
			try {
				double doubleValue = currencyFormat.parse(stringValue).doubleValue();
				if (doubleValue < 0.0) {
					return ValidationStatus.error(cannotBeNegativeMessage);
				}
				return Status.OK_STATUS;
			} catch (ParseException e) {
				return ValidationStatus.error(mustBeCurrencyMessage);
			}
		};
