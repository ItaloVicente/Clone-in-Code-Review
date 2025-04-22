	@Override
	public boolean equals(Object o) {
		if (o instanceof PackBitmapIndexV1)
			return getPackIndex() == ((PackBitmapIndexV1) o).getPackIndex();
		return false;
	}
