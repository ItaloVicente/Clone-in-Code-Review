	private void setExistingTag(Object tagObject) {
		if (tagObject instanceof RevTag)
			existingTag = (RevTag) tagObject;
		else {
			setNoExistingTag();
			setErrorMessage(UIText.CreateTagDialog_LightweightTagMessage);
			return;
