	private String name;

	private IPath fullPath;

	GitResourceVariant(Repository repo, RevCommit revCommit, String path)
			throws IOException {
		this.repo = repo;
		this.revCommit = revCommit;
		TreeWalk tw = getTreeWalk(repo, revCommit.getTree(), path);
		if (tw == null) {
			objectId = null;
			this.path = null;
		} else {
			objectId = tw.getObjectId(0);
			this.path = new String(tw.getRawPath());
		}
