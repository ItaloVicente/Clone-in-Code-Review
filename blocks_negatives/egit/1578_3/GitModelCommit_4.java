		super(parent);
		kind = direction;
		remoteCommit = commit;
		ancestorCommit = calculateAncestor(remoteCommit);

		RevCommit[] parents = remoteCommit.getParents();
		if (parents != null && parents.length > 0)
			baseCommit = remoteCommit.getParent(0);
		else {
			baseCommit = null;
		}
	}

	@Override
	public GitModelObject[] getChildren() {
		if (children == null)
			getChildrenImpl();

		return children;

	}

	@Override
	public String getName() {
		if (name == null)
			name = remoteCommit.getShortMessage();

		return name;
	}

	@Override
	public IProject[] getProjects() {
		return getParent().getProjects();
	}

	/**
	 * Returns common ancestor for this commit and all it parent's commits.
	 *
	 * @return common ancestor commit
	 */
	public RevCommit getAncestorCommit() {
		return ancestorCommit;
	}

	/**
	 * Returns instance of commit that is parent for one that is associated with
	 * this model object.
	 *
	 * @return base commit
	 */
	public RevCommit getBaseCommit() {
		return baseCommit;
	}

	/**
	 * Resurns instance of commit that is associated with this model object.
	 *
	 * @return rev commit
	 */
	public RevCommit getRemoteCommit() {
		return remoteCommit;
	}

	@Override
	public IPath getLocation() {
		return getParent().getLocation();
