	/**
	 * Construct a iterator tat returns the input element an
	 * infinite number of times.
	 *
	 * @param element the element that #next() should return
	 */
	public SingleElementInfiniteIterator(T element) {
		this.element = element;
	}
