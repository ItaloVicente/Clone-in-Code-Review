	public int getEntryPathHashCode() {
		int hash = 0;
		for (int i = Math.max(0
			byte c = path[i];
			if (c != ' ')
				hash = (hash >>> 2) + (c << 24);
		}
		return hash;
	}

