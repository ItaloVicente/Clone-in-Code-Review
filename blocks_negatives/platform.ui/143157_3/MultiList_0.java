		super(realm);
		this.lists = new ArrayList<IObservableList<E>>(lists.length);
		this.lists.addAll(Arrays.asList(lists));
		this.elementType = elementType;

		for (IObservableList<E> list : lists) {
			Assert.isTrue(realm.equals(list.getRealm()),
		}
