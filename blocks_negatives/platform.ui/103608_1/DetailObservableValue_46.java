	IValueChangeListener<M> outerChangeListener = new IValueChangeListener<M>() {
		@Override
		public void handleValueChange(ValueChangeEvent<? extends M> event) {
			if (isDisposed())
				return;
			ObservableTracker.setIgnore(true);
			try {
				T oldValue = doGetValue();
				updateInnerObservableValue();
				fireValueChange(Diffs.createValueDiff(oldValue, doGetValue()));
			} finally {
				ObservableTracker.setIgnore(false);
			}
