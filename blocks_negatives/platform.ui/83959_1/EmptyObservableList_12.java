
	@Override
	public boolean equals(Object obj) {
		checkRealm();
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof List))
			return false;

		return ((List<?>) obj).isEmpty();
	}

	@Override
	public int hashCode() {
		checkRealm();
		return 1;
	}
