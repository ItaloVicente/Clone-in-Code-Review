	private Map<MPart, Boolean> getPartToVisibilityMap(CTabFolder tabFolder) {
		Map<MPart, Boolean> ret = new HashMap<MPart, Boolean>();
		int numItems = tabFolder.getItemCount();
		for (int i = 0; i < numItems; i++) {
			CTabItem item = tabFolder.getItem(i);
			MPart data = (MPart) item.getData(OWNING_ME);
			ret.put(data, item.isShowing());
		}
		return ret;
	}

