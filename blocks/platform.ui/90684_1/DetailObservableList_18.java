	IValueChangeListener<M> outerChangeListener = event -> {
		if (isDisposed())
			return;
		ObservableTracker.setIgnore(true);
		try {
			List<E> oldList = new ArrayList<E>(wrappedList);
			updateInnerObservableList();
			fireListChange(Diffs.computeListDiff(oldList, wrappedList));
		} finally {
			ObservableTracker.setIgnore(false);
