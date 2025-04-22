	@Override
	public void create() {
		super.create();

		List<RepositoryTreeNode> roots = new ArrayList<RepositoryTreeNode>();
		roots.add(localBranches);
		roots.add(remoteBranches);
		roots.add(tags);

		branchTree.setInput(roots);

		try {
			String fullBranch = repo.getFullBranch();
			if (!markRef(fullBranch))
				branchTree.expandToLevel(localBranches, 1);
		} catch (IOException e) {
		}
	}

	private boolean markRef(String refName) {
		if (refName == null)
			return false;

		RepositoryTreeNode node;
		try {
			if (refName.startsWith(Constants.R_HEADS)) {
				Ref ref = this.repo.getRef(refName);
				node = new RefNode(localBranches, this.repo, ref);
			} else {
				String mappedRef = Activator.getDefault().getRepositoryUtil()
						.mapCommitToRef(this.repo, refName, false);
				if (mappedRef != null
						&& mappedRef.startsWith(Constants.R_REMOTES)) {
					Ref ref = this.repo.getRef(mappedRef);
					node = new RefNode(remoteBranches, this.repo, ref);
				} else if (mappedRef != null
						&& mappedRef.startsWith(Constants.R_TAGS)) {
					Ref ref = this.repo.getRef(mappedRef);
					node = new TagNode(tags, this.repo, ref);
				} else {
					return false;
				}
			}
		} catch (IOException e) {
			return false;
		}

		branchTree.setSelection(new StructuredSelection(node), true);
		return true;
	}

	/**
	 * @return the selected refName
	 */
	public String getRefName() {
		return this.selectedBranch;
	}

	@Override
	protected void okPressed() {
		this.selectedBranch = refNameFromDialog();
		super.okPressed();
	}

	private String refNameFromDialog() {
		IStructuredSelection sel = (IStructuredSelection) branchTree
				.getSelection();
		if (sel.size() != 1)
			return null;
		RepositoryTreeNode node = (RepositoryTreeNode) sel.getFirstElement();
		if (node.getType() == RepositoryTreeNodeType.REF
				|| node.getType() == RepositoryTreeNodeType.TAG) {
			return ((Ref) node.getObject()).getName();
		}
		return null;
	}

	private InputDialog getRefNameInputDialog(String prompt, final String refPrefix) {
		InputDialog labelDialog = new InputDialog(
				getShell(),
				UIText.BranchSelectionDialog_QuestionNewBranchTitle,
				prompt,
