	@Override
	public int hashCode() {
		return Objects.hash(getOid()) * 31 + Long.hashCode(getSize());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		LfsPointer other = (LfsPointer) obj;
		return Objects.equals(getOid()
				&& getSize() == other.getSize();
	}
}
