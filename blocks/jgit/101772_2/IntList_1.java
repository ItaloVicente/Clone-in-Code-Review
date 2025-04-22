	public boolean contains(int value) {
		for (int i = 0; i < count; i++)
			if (entries[i] == value)
				return true;
		return false;
	}

