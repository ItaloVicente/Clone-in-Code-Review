	@Override
	public boolean equals(Object o) {
		getterCalled();
		return getList().equals(o);
	}

	@Override
	public int hashCode() {
		getterCalled();
		return getList().hashCode();
	}

