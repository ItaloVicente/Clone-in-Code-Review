	@Override
	public Iterator<E> iterator() {
		@SuppressWarnings("unchecked")
		E[] objects = (E[]) new Object[0];
		return Arrays.asList(elements == null ? objects : elements).iterator();
	}
