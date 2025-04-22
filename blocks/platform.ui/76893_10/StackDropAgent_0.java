		return itemRects;
	}

	private ArrayList<Rectangle> computeInsertRects() {
		List<CTabItem> visibleItems = getVisibleItems(dropCTF);
		return getItemRects(dropCTF, visibleItems, tabArea);
