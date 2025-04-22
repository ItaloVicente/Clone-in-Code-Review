		final byte[] buf;
		FileSnapshot otherSnapshot = FileSnapshot.save(path);
		try {
			buf = IO.readSome(path, limit);
		} catch (FileNotFoundException noFile) {
			if (path.isFile()) {
				throw noFile;
