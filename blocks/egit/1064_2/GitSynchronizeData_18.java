	public Commit getCommit() {
		try {
			return repo.mapCommit(srcRev);
		} catch (IOException e) {
			return null;
		}
	}

