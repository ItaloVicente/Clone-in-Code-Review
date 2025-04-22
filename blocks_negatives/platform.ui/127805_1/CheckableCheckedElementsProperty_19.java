		Set wrappedSet = ViewerElementSet.withComparer(comparer);
		IObservableSet observable = new CheckableCheckedElementsObservableSet(
				realm, wrappedSet, elementType, comparer, (ICheckable) source);
		if (source instanceof Viewer)
			observable = new ViewerObservableSetDecorator(observable,
					(Viewer) source);
		return observable;
