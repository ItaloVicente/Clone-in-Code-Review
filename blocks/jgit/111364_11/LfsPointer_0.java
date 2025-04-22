
	@Override
	public int compareTo(LfsPointer o) {
		int x = getOid().compareTo(o.getOid());
		if (x != 0) {
			return 0;
		}

		return (int) (getSize() - o.getSize());
	}
