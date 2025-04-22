
	@Override
	public NodeList getVisibleChildNodes() {
		CTabFolder folder = (CTabFolder) getWidget();
		ArrayList<Widget> visible = new ArrayList<Widget>();

		if (folder.getTopRight() != null) {
			visible.add(folder.getTopRight());
		}
		Collections.addAll(visible, folder.getItems());
		int selected = folder.getSelectionIndex();
		if (selected >= 0) {
			CTabItem item = folder.getItem(selected);
			if (item.getControl() != null) {
				visible.add(item.getControl());
			}
		}
		return new ArrayNodeList(visible, engine);
	}
