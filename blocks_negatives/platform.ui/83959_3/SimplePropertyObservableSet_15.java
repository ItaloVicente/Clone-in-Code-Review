	@Override
	public boolean equals(Object o) {
		getterCalled();
		return getSet().equals(o);
	}

	@Override
	public int hashCode() {
		getterCalled();
		return getSet().hashCode();
	}

