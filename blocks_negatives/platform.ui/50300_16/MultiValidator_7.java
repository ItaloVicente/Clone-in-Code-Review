		@Override
		public void handleRemove(int index, Object element) {
			IObservable dependency = (IObservable) element;
			dependency.removeChangeListener(dependencyListener);
			dependency.removeStaleListener(dependencyListener);
		}
	});
