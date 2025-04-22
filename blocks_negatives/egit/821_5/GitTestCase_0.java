		rmrf(gitDir);
	}

	private void rmrf(File d) throws IOException {
		if (!d.exists())
			return;

		File[] files = d.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; ++i) {
				if (files[i].isDirectory())
					rmrf(files[i]);
				else if (!files[i].delete())
					throw new IOException(files[i] + " in use or undeletable");
			}
		}
		if (!d.delete())
			throw new IOException(d + " in use or undeletable");
		assert !d.exists();
