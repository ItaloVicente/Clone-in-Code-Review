	FileReftableDatabase(FileRepository repo) throws IOException {
		this(repo
			new File(repo.getDirectory()
			Constants.TABLES_LIST));
	}

