		final ObjectId name = pw.computeName();
		final File packFile = fullPackFileName(name, ".pack");
		final File idxFile = fullPackFileName(name, ".idx");
		final File[] files = new File[] { packFile, idxFile };
		write(files, pw);
		pw.release();
		return files;
