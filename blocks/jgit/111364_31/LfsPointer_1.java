
	@Override
	public int compareTo(LfsPointer o) {
		int x = getOid().compareTo(o.getOid());
		if (x != 0) {
			return x;
		}

		return (int) (getSize() - o.getSize());
	}
