	@Override
	public boolean isCaseSensitive() {
		if (isMacOS())
			return false;
		else
			return true;
	}

