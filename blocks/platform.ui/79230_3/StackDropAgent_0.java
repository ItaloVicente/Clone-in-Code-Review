	private static List<CTabItem> getVisibleItems(CTabFolder dropCTF) {
		List<CTabItem> visibleItems = new ArrayList<>();
		for (CTabItem item : dropCTF.getItems()) {
			if (item.isShowing()) {
				visibleItems.add(item);
			}
		}
		return visibleItems;
	}
