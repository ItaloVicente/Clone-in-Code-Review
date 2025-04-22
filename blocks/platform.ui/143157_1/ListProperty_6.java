	@Override
	public <U extends S> IObservableList<E> observeDetail(IObservableList<U> masterList) {
		IObservableList<IObservableList<E>> nestedLists = MasterDetailObservables.detailValues(masterList,
				master -> Observables.constantObservableValue(masterList.getRealm(), observe(master),
						IObservableList.class),
				getElementType());

		MultiList<E> multiList = new MultiList<>(masterList.getRealm(), nestedLists, getElementType());

		IListChangeListener<E> dummy = e -> {};
		multiList.addListChangeListener(dummy);

		nestedLists.addListChangeListener(event -> event.diff.accept(new ListDiffVisitor<IObservableList<E>>() {
			@Override
			public void handleRemove(int index, IObservableList<E> element) {
				element.dispose();
			}

			@Override public void handleAdd(int index, IObservableList<E> element) {}
			@Override public void handleMove(int oldIndex, int newIndex, IObservableList<E> element) {}
		}));

		return multiList;
	}

