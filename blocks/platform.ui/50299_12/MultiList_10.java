	@Deprecated
	public MultiList(IObservableList<E>[] lists, Object elementType) {
		this(Realm.getDefault(), lists, elementType);
	}

	public MultiList(List<IObservableList<E>> lists, Object elementType) {
