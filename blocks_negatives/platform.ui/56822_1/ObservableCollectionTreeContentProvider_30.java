		elementSetFactory = new IObservableFactory() {
			@Override
			public IObservable createObservable(Object target) {
				return ObservableViewerElementSet.withComparer(realm, null,
						getElementComparer((Viewer) target));
			}
		};
