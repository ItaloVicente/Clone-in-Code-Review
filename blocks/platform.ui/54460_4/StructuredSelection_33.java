	@SuppressWarnings("rawtypes")
	public static final StructuredSelection<?> EMPTY = new StructuredSelection();

	public StructuredSelection() {
	}


	public StructuredSelection(E[] elements) {
		Assert.isNotNull(elements);
		@SuppressWarnings("unchecked")
		E[] castedArray = (E[]) new Object[elements.length];
		this.elements = castedArray;
		System.arraycopy(elements, 0, this.elements, 0, elements.length);
	}

	public StructuredSelection(E element) {
		Assert.isNotNull(element);
		@SuppressWarnings("unchecked")
		E[] castedArray = (E[]) new Object[] { element };
		elements = castedArray;
	}

	public StructuredSelection(List<E> elements) {
		this(elements, null);
	}

