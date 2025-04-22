        Text text = new Text(getComposite(), SWT.BORDER);
        adventure.setPrice(5.0);
        final String cannotBeNegativeMessage = "Price cannot be negative.";
        final String mustBeCurrencyMessage = "Price must be a currency.";

        IValidator validator = new IValidator() {
            @Override
			public IStatus validate(Object value) {
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
            }
        };

