		final File[] all = directory.listFiles();
		if (all == null)
			return EOF;
		final Entry[] r = new Entry[all.length];
		for (int i = 0; i < r.length; i++)
			r[i] = new FileEntry(all[i], fs, fileModeStrategy);
		return r;
