	}

	public StructuredSelection(Object[] elements) {
		Assert.isNotNull(elements);
		this.elements = new Object[elements.length];
		System.arraycopy(elements, 0, this.elements, 0, elements.length);
