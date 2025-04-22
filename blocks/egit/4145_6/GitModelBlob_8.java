	public GitModelBlob(GitModelObjectContainer parent, Repository repo,
			Change change, IPath path) {
		super(parent);
		this.repo = repo;
		this.path = path;
		this.change = change;
