
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof DiffEntry))
			return false;
		DiffEntry other = (DiffEntry) o;
				&& score == other.score;
	}
