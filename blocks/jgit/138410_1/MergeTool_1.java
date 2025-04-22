	private void addFile(String fileName) throws Exception {
		try (Git git = new Git(db)) {
			git.add().addFilepattern(fileName).call();
		}
	}

	private void rmFile(String fileName) throws Exception {
		try (Git git = new Git(db)) {
			git.rm().addFilepattern(fileName).call();
		}
	}

