			Assert.isTrue(realm.equals(lists[i].getRealm()),
					"All source lists in a MultiList must belong to the same realm"); //$NON-NLS-1$
		}
	}

	public MultiList(Realm realm, List<IObservableList<E>> lists,
			Object elementType) {
		super(realm);
		this.lists = lists;
		this.elementType = elementType;

		for (IObservableList<E> list : lists) {
			Assert.isTrue(realm.equals(list.getRealm()),
					"All source lists in a MultiList must belong to the same realm"); //$NON-NLS-1$
