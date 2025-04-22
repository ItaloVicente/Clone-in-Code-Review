
	@Override
	public <U extends S> IObservableList<E> observeDetail(IObservableList<U> masterList) {
		IObservableList<IObservableList<E>> nestedLists = MasterDetailObservables.detailValues(masterList,
				master -> new DisposingObservableWrapper<>(masterList.getRealm(), observe(master)), getElementType(),
				true);

		MasterDetailObservables.detailValues(masterList,
				master -> new DisposingObservableWrapper<>(masterList.getRealm(), observe(master)),
				getElementType(), true);

		return new MultiList<>(masterList.getRealm(), nestedLists, getElementType());
	}


