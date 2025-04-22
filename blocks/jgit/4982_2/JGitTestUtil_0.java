
	public static File writeTrashFile(final FileRepository db
			final String name
		File path = new File(db.getWorkTree()
		write(path
		return path;
	}

	public static void write(final File f
			throws IOException {
		FileUtils.mkdirs(f.getParentFile()
		Writer w = new OutputStreamWriter(new FileOutputStream(f)
		try {
			w.write(body);
		} finally {
			w.close();
		}
	}

	public static void deleteTrashFile(final FileRepository db
			final String name) throws IOException {
		File path = new File(db.getWorkTree()
		FileUtils.delete(path);
	}

