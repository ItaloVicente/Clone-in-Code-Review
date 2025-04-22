		kind = direction;
		if (commit != null) {
			baseCommit = commit;

			RevCommit[] parents = baseCommit.getParents();
			if (parents != null && parents.length > 0)
				remoteCommit = new RevWalk(getRepository())
						.parseCommit(getParentRevCommit());
			else {
				remoteCommit = null;
			}
		} else {
			baseCommit = null;
			remoteCommit = null;
		}
	}

	public Image getImage() {
		return null;
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

	public int getKind() {
		if (kind == -1 || kind == LEFT || kind == RIGHT)
			calculateKind();

		return kind;
	}

	@Override
	public GitModelObject[] getChildren() {
		if (children == null)
			children = getChildrenImpl();

		return children;

	}

	@Override
	public String getName() {
		if (name == null)
			name = baseCommit.getShortMessage();

		return name;
	}

	@Override
	public IProject[] getProjects() {
		return getParent().getProjects();
	}

	@Override
	public abstract IPath getLocation();

	public ITypedElement getAncestor() {
		return null;
	}

	public ITypedElement getLeft() {
		return null;
	}

	public ITypedElement getRight() {
		return null;
	}

	public void addCompareInputChangeListener(
			ICompareInputChangeListener listener) {
	}

	public void removeCompareInputChangeListener(
			ICompareInputChangeListener listener) {
	}

	public void copy(boolean leftToRight) {
