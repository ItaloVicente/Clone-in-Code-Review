	@Override
	public void reset() {
		if (!first()) {
			ptr = treeStart;
			if (!eof())
				parseEntry();
		}
	}

