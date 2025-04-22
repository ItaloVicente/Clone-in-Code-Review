	}

	private void createDir(File f) throws IOException {
		if (!db.getFS().isDirectory(f) && !f.mkdirs()) {
			File p = f;
			while (p != null && !db.getFS().exists(p))
				p = p.getParentFile();
			if (p == null || db.getFS().isDirectory(p))
				throw new IOException(JGitText.get().cannotCreateDirectory);
			FileUtils.delete(p);
			if (!f.mkdirs())
				throw new IOException(JGitText.get().cannotCreateDirectory);
