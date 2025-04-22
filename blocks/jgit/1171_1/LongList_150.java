	public boolean contains(final long value) {
		for (int i = 0; i < count; i++)
			if (entries[i] == value)
				return true;
		return false;
	}

