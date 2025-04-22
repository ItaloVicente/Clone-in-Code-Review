	public MultiList(Realm realm, List<? extends IObservableList<E>> lists, Object elementType) {
		this(realm, Observables.staticObservableList(realm, lists, elementType), elementType);
	}

	public MultiList(IObservableList<? extends IObservableList<E>> lists) {
		this(Realm.getDefault(), lists, null);
	}

	public MultiList(IObservableList<? extends IObservableList<E>> lists, Object elementType) {
		this(Realm.getDefault(), lists, elementType);
	}

	public MultiList(Realm realm, IObservableList<? extends IObservableList<E>> lists, Object elementType) {
