		Text text = new Text(getComposite(), SWT.BORDER);
		adventure.setPrice(5.0);
		final String cannotBeNegativeMessage = "Price cannot be negative.";
		final String mustBeCurrencyMessage = "Price must be a currency.";
		final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);

		IConverter toCurrency = new Converter(double.class, String.class) {
			@Override
