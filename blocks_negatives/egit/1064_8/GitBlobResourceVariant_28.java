	private RevCommitList<RevCommit> commitList;

	GitBlobResourceVariant(IResource resource, Repository repository,
			ObjectId id, RevCommitList<RevCommit> commitList) {
		super(resource);
		this.repository = repository;
		this.id = id;
		this.commitList = commitList;
	}
