	private IValueChangeListener<M> masterChangeListener = event -> {
		if (isDisposed())
			return;
		ObservableTracker.setIgnore(true);
		try {
			Map<K, V> oldMap = new HashMap<K, V>(wrappedMap);
			updateDetailMap();
			fireMapChange(Diffs.computeMapDiff(oldMap, wrappedMap));
		} finally {
			ObservableTracker.setIgnore(false);
