	IValueChangeListener<M> outerChangeListener = (ValueChangeEvent<? extends M> event) -> {
		if (isDisposed())
			return;
		ObservableTracker.setIgnore(true);
		try {
			Set<E> oldSet = new HashSet<>(wrappedSet);
			updateInnerObservableSet();
			fireSetChange(Diffs.computeSetDiff(oldSet, wrappedSet));
		} finally {
			ObservableTracker.setIgnore(false);
