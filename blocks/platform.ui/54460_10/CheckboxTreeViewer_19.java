	private void applyState(CustomHashtable checked, CustomHashtable grayed,
			Widget widget) {
		Item[] items = getChildren(widget);
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];
			if (item instanceof TreeItem) {
				Object data = item.getData();
				if (data != null) {
					TreeItem ti = (TreeItem) item;
					ti.setChecked(checked.containsKey(data));
					ti.setGrayed(grayed.containsKey(data));
				}
			}
			applyState(checked, grayed, item);
		}
	}

	protected void fireCheckStateChanged(final CheckStateChangedEvent event) {
		Object[] array = checkStateListeners.getListeners();
		for (int i = 0; i < array.length; i++) {
			final ICheckStateListener l = (ICheckStateListener) array[i];
			SafeRunnable.run(new SafeRunnable() {
				@Override
