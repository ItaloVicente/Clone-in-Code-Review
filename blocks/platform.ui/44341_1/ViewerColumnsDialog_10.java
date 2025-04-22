	void setColumnsObjs(T[] columnObjs) {
		IColumnInfoProvider<T> columnInfo = doGetColumnInfoProvider();
		IColumnUpdater<T> updater = doGetColumnUpdater();
		@SuppressWarnings("hiding")
		List<T> visible = getVisible();
		@SuppressWarnings("hiding")
		List<T> nonVisible = getNonVisible();
