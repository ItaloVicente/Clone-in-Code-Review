	public UnionSet(IObservableSet<? extends E>[] childSets) {
		this(new HashSet<IObservableSet<? extends E>>(Arrays.asList(childSets)));
	}

	public UnionSet(Set<IObservableSet<? extends E>> childSets) {
		this(childSets, childSets.iterator().next().getElementType());
