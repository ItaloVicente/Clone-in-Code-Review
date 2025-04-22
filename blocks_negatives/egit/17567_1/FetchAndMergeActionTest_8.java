	private String prepare() throws Exception {
		deleteAllProjects();
		shareProjects(repositoryFile);
		Repository repo = lookupRepository(repositoryFile);
		RevWalk rw = new RevWalk(repo);
		ObjectId id = repo.resolve(repo.getFullBranch());
		String commitId = rw.parseCommit(id).name();
