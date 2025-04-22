	/**
	 *
	 * @param parent
	 *            of particular model object
	 */
	protected GitModelObject(GitModelObject parent) {
		this.parent = parent;
	}

	/**
	 * @return parent of particular model object, or <code>null</code> if object
	 *         is root node
	 */
	public GitModelObject getParent() {
		return parent;
	}

	/**
	 * @return repository associated with particular model object
	 */
	public Repository getRepository() {
		return parent.getRepository();
	}

	/**
	 * Returns preinitialized instance of {@link TreeWalk}. Set of
	 * initialization call's is common for all model object's.
	 *
	 * @return tree walk
	 */
	protected TreeWalk createTreeWalk() {
		Repository repo = getRepository();
		TreeWalk tw = new TreeWalk(repo);

		tw.reset();
		tw.setRecursive(false);

		tw.setFilter(ANY_DIFF);
		return tw;
	}

