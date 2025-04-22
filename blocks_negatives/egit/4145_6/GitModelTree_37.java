	public GitModelTree(GitModelObjectContainer parent, RevCommit commit,
			RevCommit ancestorCommit, ObjectId ancestorId, ObjectId baseId,
			ObjectId remoteId, IPath location) throws IOException {
		super(parent, commit, ancestorCommit, parent.getKind() & (LEFT | RIGHT));
		this.baseId = baseId;
		this.remoteId = remoteId;
		this.ancestorId = ancestorId;
		this.location = location;
