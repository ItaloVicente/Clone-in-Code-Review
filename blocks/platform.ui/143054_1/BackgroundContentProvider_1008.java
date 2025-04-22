	public BackgroundContentProvider(AbstractVirtualTable table,
			IConcurrentModel model, Comparator sortOrder) {

		updator = new ConcurrentTableUpdator(table);
		this.model = model;
		this.sortOrder = sortOrder;
		model.addListener(listener);
	}

	public void dispose() {
		cancelSortJob();
		updator.dispose();
		model.removeListener(listener);
	}

	public void refresh() {
		if (updator.isDisposed()) {
			return;
		}
		model.requestUpdate(listener);
	}

	private void doSort(IProgressMonitor mon) {

		mon.setCanceled(false);

		mon.beginTask(SORTING, 100);

		Comparator order = sortOrder;
		IFilter f = filter;
		LazySortedCollection collection = new LazySortedCollection(order);

		Object[] knownObjects = updator.getKnownObjects();
		for (Object object : knownObjects) {
