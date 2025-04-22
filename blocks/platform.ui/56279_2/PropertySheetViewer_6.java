				setErrorMessage(entry.getErrorText());
			}
		};
	}

	private void createItem(Object node, Widget parent, int index) {
		TreeItem item;
		if (parent instanceof TreeItem) {
