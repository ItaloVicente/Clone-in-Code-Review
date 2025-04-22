	IValueChangeListener<M> outerChangeListener = new IValueChangeListener<M>() {
		@Override
		public void handleValueChange(ValueChangeEvent<? extends M> event) {
			if (isDisposed())
				return;
			ObservableTracker.setIgnore(true);
			try {
				List<E> oldList = new ArrayList<E>(wrappedList);
				updateInnerObservableList();
				fireListChange(Diffs.computeListDiff(oldList, wrappedList));
			} finally {
				ObservableTracker.setIgnore(false);
			}
