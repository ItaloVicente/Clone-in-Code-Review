	public UnionSet(IObservableSet[] childSets, Object elementType) {
		super(childSets[0].getRealm(), null, elementType);
		System.arraycopy(childSets, 0,
				this.childSets = new IObservableSet[childSets.length], 0,
				childSets.length);
		this.stalenessTracker = new StalenessTracker(childSets,
				stalenessConsumer);
