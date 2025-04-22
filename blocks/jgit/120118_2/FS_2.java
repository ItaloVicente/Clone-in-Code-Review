	public Entry[] list(File directory
		final File[] all = directory.listFiles();
		if (all == null) {
			return NO_ENTRIES;
		}
		final Entry[] result = new Entry[all.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new FileEntry(all[i]
		}
		return result;
	}

