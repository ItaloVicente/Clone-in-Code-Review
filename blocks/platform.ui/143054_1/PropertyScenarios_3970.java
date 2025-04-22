				try {
					return new Double(currencyFormat.parse((String) fromObject).doubleValue());
				} catch (ParseException e) {
					return new Double(0);
				}
			}
		};

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

		getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify),
				BeansObservables.observeValue(adventure, "price"),
