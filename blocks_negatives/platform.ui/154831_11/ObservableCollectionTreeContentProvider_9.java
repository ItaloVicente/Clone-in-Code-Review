		elementSetFactory = new IObservableFactory<Viewer, IObservableSet<E>>() {
			@Override
			public IObservableSet<E> createObservable(Viewer target) {
				return ObservableViewerElementSet.withComparer(realm, null, getElementComparer(target));
			}
		};
