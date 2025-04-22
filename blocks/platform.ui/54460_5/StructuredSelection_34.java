	public StructuredSelection(List<E> elements, IElementComparer comparer) {
		Assert.isNotNull(elements);
		@SuppressWarnings("unchecked")
		E[] castedArray = (E[]) elements.toArray();
		this.elements = castedArray;
		this.comparer = comparer;
