	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (getClass() == obj.getClass()) {
			DecoratingObservable other = (DecoratingObservable) obj;
			return Util.equals(this.decorated, other.decorated);
		}
		return Util.equals(decorated, obj);
	}

	@Override
	public int hashCode() {
		return decorated.hashCode();
	}

