	@Override
	public boolean equals(Object o) {
		getterCalled();
		return o == this || wrappedSet.equals(o);
	}

	@Override
	public int hashCode() {
		getterCalled();
		return wrappedSet.hashCode();
	}

