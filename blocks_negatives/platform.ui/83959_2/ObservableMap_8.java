	@Override
	public boolean equals(Object o) {
		getterCalled();
		return o == this || wrappedMap.equals(o);
	}

	@Override
	public int hashCode() {
		getterCalled();
		return wrappedMap.hashCode();
	}

