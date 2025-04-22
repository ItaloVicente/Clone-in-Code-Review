		close(ref.pack);
	}

	private void close(final PackFile pack) {
		if (pack.endWindowCache())
			openFiles.decrementAndGet();
