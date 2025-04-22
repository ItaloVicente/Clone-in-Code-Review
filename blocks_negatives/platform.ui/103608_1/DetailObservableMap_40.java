	private IValueChangeListener<M> masterChangeListener = new IValueChangeListener<M>() {
		@Override
		public void handleValueChange(ValueChangeEvent<? extends M> event) {
			if (isDisposed())
				return;
			ObservableTracker.setIgnore(true);
			try {
				Map<K, V> oldMap = new HashMap<K, V>(wrappedMap);
				updateDetailMap();
				fireMapChange(Diffs.computeMapDiff(oldMap, wrappedMap));
			} finally {
				ObservableTracker.setIgnore(false);
			}
