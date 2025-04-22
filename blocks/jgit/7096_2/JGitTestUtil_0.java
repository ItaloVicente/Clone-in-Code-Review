	public static File writeTrashFile(final FileRepository db
			final String subdir
			final String name
		File path = new File(db.getWorkTree() + "/" + subdir
		write(path
		return path;
	}

