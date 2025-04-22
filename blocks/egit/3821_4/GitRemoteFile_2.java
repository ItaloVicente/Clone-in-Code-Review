	private final Repository repo;

	GitRemoteFile(Repository repo, RevCommit commitId, ObjectId objectId,
			String path) {
		super(commitId, objectId, path);
		this.repo = repo;
