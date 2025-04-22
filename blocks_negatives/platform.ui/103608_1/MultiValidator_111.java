		public void handleListChange(ListChangeEvent event) {
			event.diff.accept(new ListDiffVisitor() {
				@Override
				public void handleAdd(int index, Object element) {
					IObservable dependency = (IObservable) element;
					dependency.addChangeListener(dependencyListener);
					dependency.addStaleListener(dependencyListener);
				}
