	public GitModelCommit(GitModelRepository parent, Repository repo,
			Commit commit, IProject[] projects) {
		super(parent);
		this.repo = repo;
		this.commit = commit;
		this.projects = projects;
	}
