		GitDateFormatter.Format initialFormat = fromString(initialValue);
		FormatInfo info = DATA.get(initialFormat);
		dateFormat.setEnabled(initialFormat == null, pane);
		dateFormat.setStringValue(
				initialFormat == null ? lastCustomValue : info.format);
		formatExplanation.setText(info.explanation);
