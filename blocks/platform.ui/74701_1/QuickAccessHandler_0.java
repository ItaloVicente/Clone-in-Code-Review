		LinkedList<QuickAccessElement> previousPicksList = null;
		if (searchField != null && searchField.getObject() instanceof SearchField) {
			SearchField field = (SearchField) searchField.getObject();
			previousPicksList = field.getPreviousPicksList();
		}

