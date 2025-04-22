	public MultiList(Realm realm, List<? extends IObservableList<E>> topLevelList, Object elementType) {
		this(realm, Observables.staticObservableList(realm, topLevelList, elementType), elementType);
	}

	public MultiList(IObservableList<? extends IObservableList<E>> topLevelList) {
		this(Realm.getDefault(), topLevelList, null);
	}

	public MultiList(IObservableList<? extends IObservableList<E>> topLevelList, Object elementType) {
		this(Realm.getDefault(), topLevelList, elementType);
	}

	public MultiList(Realm realm, IObservableList<? extends IObservableList<E>> topLevelList, Object elementType) {
