	@Override
	public boolean equals(Object o) {
		getterCalled();
		return o == this || wrappedList.equals(o);
	}

	@Override
	public int hashCode() {
		getterCalled();
		return wrappedList.hashCode();
	}

