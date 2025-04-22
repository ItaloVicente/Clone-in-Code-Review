
	@Override
	public NodeList getVisibleChildNodes() {
		CTabFolder folder = (CTabFolder) getWidget();
		int selected = folder.getSelectionIndex();
		if (selected < 0) {
			return null;
		}

		CTabItem item = folder.getItem(selected);

		if (item.getControl() == null) {
			return null;
		}
		return new ArrayNodeList(new Object[] { item, item.getControl() },
				getEngine(folder));
	}
