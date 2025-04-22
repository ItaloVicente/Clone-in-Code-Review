		Assert.isTrue(text != null && (value instanceof String));
		text.removeModifyListener(getModifyListener());
		text.setText((String) value);
		text.addModifyListener(getModifyListener());
	}

	protected void editOccured(ModifyEvent e) {
		String value = text.getText();
		if (value == null) {
