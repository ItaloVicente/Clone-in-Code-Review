	public static Git open(File dir) throws IOException {
		return open(dir
	}

	public static Git open(File dir
		RepositoryCache.FileKey key;

		key = RepositoryCache.FileKey.lenient(dir
		return wrap(new RepositoryBuilder()
				.setFS(fs)
				.setGitDir(key.getFile())
				.setMustExist(true).build());
	}

	public static Git wrap(Repository repo) {
		return new Git(repo);
	}

