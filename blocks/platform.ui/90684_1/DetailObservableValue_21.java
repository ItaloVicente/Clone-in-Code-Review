	IValueChangeListener<M> outerChangeListener = event -> {
		if (isDisposed())
			return;
		ObservableTracker.setIgnore(true);
		try {
			T oldValue = doGetValue();
			updateInnerObservableValue();
			fireValueChange(Diffs.createValueDiff(oldValue, doGetValue()));
		} finally {
			ObservableTracker.setIgnore(false);
