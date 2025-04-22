		File f;
		try {
			f = gitdir.getCanonicalFile();
		} catch (IOException ioe) {
			f = gitdir.getAbsoluteFile();
		}
