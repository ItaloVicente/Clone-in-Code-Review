	public CollectionChildNodeIterator(
		CollectionPointer pointer,
		NodeTest test,
		boolean reverse,
		NodePointer startWith) {
		super(pointer, reverse, startWith);
		this.test = test;
	}
