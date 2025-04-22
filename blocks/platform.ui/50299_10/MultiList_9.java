	@Deprecated
	public MultiList(IObservableList<E>[] lists) {
		this(Realm.getDefault(), lists, null);
	}

	public MultiList(List<IObservableList<E>> lists) {
