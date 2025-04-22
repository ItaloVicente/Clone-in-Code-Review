		super.setAllSelections(value);
	}

	protected void setSourceName(String path) {

		if (path.length() > 0) {

			this.currentSourceName = path;
			String[] currentItems = this.sourceNameField.getItems();
			int selectionIndex = -1;
			for (int i = 0; i < currentItems.length; i++) {
				if (currentItems[i].equals(path)) {
