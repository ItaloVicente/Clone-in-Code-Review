	@Override
	public boolean equals(Object o) {
		getterCalled();
		return getWrappedSet().equals(o);
	}

	@Override
	public int hashCode() {
		getterCalled();
		return getWrappedSet().hashCode();
	}

