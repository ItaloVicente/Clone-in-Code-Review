	@Override
	public boolean equals(Object obj) {
		getterCalled();
		if (this == obj) {
			return true;
		}
		return decorated.equals(obj);
	}

	@Override
	public int hashCode() {
		getterCalled();
		return decorated.hashCode();
	}

