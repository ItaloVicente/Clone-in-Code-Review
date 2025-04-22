	IListChangeListener<IObservable> targetsListener = event -> {
		event.diff.accept(new ListDiffVisitor<IObservable>() {
			@Override
			public void handleAdd(int index, IObservable dependency) {
				dependency.addChangeListener(dependencyListener);
				dependency.addStaleListener(dependencyListener);
			}
