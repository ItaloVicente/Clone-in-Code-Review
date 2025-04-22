		super(realm);
		this.topLevelList = new ArrayList<IObservableList<E>>(topLevelList.length);
		this.topLevelList.addAll(Arrays.asList(topLevelList));
		this.elementType = elementType;

		for (IObservableList<E> nestedList : topLevelList) {
			Assert.isTrue(realm.equals(nestedList.getRealm()),
		}
