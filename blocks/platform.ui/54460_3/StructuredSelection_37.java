		return elements == null ? 0 : elements.length;
	}

	@Override
	public E[] toArray() {
		@SuppressWarnings("unchecked")
		E[] objects = (E[]) new Object[0];
		return elements == null ? objects : (E[]) elements.clone();
	}

	@Override
	public List<E> toList() {
		@SuppressWarnings("unchecked")
		E[] objects = (E[]) new Object[0];
		return Arrays.asList(elements == null ? objects : elements);
	}

	@Override
