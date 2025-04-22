		return addButton.getShell();
	}

	protected abstract String[] parseString(String stringList);

	private void removePressed() {
		setPresentsDefaultValue(false);
		int index = list.getSelectionIndex();
		if (index >= 0) {
			list.remove(index);
