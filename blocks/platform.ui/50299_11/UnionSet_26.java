	public UnionSet(IObservableSet<? extends E>[] childSets, Object elementType) {
		this(new HashSet<IObservableSet<? extends E>>(Arrays.asList(childSets)), elementType);
	}

	public UnionSet(Set<IObservableSet<? extends E>> childSets, Object elementType) {
		super(childSets.iterator().next().getRealm(), null, elementType);
		this.childSets = childSets;

		this.stalenessTracker = new StalenessTracker(childSets.toArray(new IObservableSet[0]), stalenessConsumer);
