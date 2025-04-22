	private final List<RevCommit> commits;
	private final Map<AnyObjectId
	private final String trackedPath;

	public CommitHistory(final List<RevCommit> commits
			final String trackedPath) {
		this.commits = commits;
		this.pathsByCommit = pathsByCommit;
		this.trackedPath = trackedPath;
	}

	public List<RevCommit> getCommits() {
		return commits;
	}

	public String getTrackedFilePath() {
		return (trackedPath == null) ? "/" : trackedPath;
	}

	public String trackedFileNameChangeFor(final AnyObjectId commitId) {
		return Optional.ofNullable(pathsByCommit.get(commitId)).map(path -> "/" + path)
				.orElseGet(() -> getTrackedFilePath());
	}
