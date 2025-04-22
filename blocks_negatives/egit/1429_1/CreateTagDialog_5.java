	private void setTagNameError(String tagNameMessage) {
		if (tagNameErrorText != null && !tagNameErrorText.isDisposed()) {
			tagNameErrorText
			boolean hasError = tagNameMessage != null
					&& (StringConverter.removeWhiteSpaces(tagNameMessage))
							.length() > 0;
			tagNameErrorText.setEnabled(hasError);
			tagNameErrorText.setVisible(hasError);
			tagNameErrorText.getParent().update();
