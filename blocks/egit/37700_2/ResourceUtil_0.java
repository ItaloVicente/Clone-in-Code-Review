	public static boolean isSymbolicLink(Repository repository,
			String repoRelativePath) {
		try {
			File f = new Path(repository.getWorkTree().getAbsolutePath())
					.append((repoRelativePath)).toFile();
			return FS.DETECTED.isSymLink(f);
		} catch (@SuppressWarnings("unused") IOException e) {
			return false;
		}
	}

