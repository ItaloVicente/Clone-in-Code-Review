	/**
	 * @param showLocalBranches show/hide the local branches root
	 * @param showRemoteBranches show/hide the remote branches root
	 * @param showTags show/hide the tag root
	 * @param showReferences show/hide the references root
	 */
	protected void setRootsToShow(boolean showLocalBranches,
			boolean showRemoteBranches, boolean showTags, boolean showReferences) {
		this.showLocalBranches = showLocalBranches;
		this.showRemoteBranches = showRemoteBranches;
		this.showTags = showTags;
		this.showReferences = showReferences;
	}
