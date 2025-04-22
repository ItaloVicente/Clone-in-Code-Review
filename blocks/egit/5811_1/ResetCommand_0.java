	private RevCommit getLatestCommit(String branch, Repository repository) {
		ObjectId resolved;
		try {
			resolved = repository.resolve(branch);
		} catch (IOException e) {
			return null;
		}
		if (resolved == null)
			return null;
		RevWalk walk = new RevWalk(repository);
