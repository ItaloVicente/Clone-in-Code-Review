		super(realm);
		this.lists = new ArrayList<IObservableList<E>>(lists.length);
		for (IObservableList<E> list : lists) {
			this.lists.add(list);
		}
		this.elementType = elementType;

		for (IObservableList<E> list : lists) {
			Assert.isTrue(realm.equals(list.getRealm()),
		}
