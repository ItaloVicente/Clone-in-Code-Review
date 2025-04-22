		super(parentShell);
		this.repo = repo;
		localBranches = new LocalBranchesNode(null, this.repo);
		remoteBranches = new RemoteBranchesNode(null, this.repo);
		tags = new TagsNode(null, this.repo);
	}

	@Override
	protected Composite createDialogArea(Composite base) {
		Composite parent = (Composite) super.createDialogArea(base);
		parent.setLayout(GridLayoutFactory.fillDefaults().create());

		FilteredTree tree = new FilteredTree(parent, SWT.SINGLE | SWT.BORDER, new PatternFilter());
		branchTree = tree.getViewer();
		branchTree.setLabelProvider(new RepositoriesViewLabelProvider());
		branchTree.setContentProvider(new RepositoriesViewContentProvider());

		GridDataFactory.fillDefaults().grab(true, true).hint(500, 300).applyTo(
				tree);
		branchTree.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {

				String refName = refNameFromDialog();

				boolean tagSelected = refName != null
						&& refName.startsWith(Constants.R_TAGS);

				boolean branchSelected = refName != null
						&& (refName.startsWith(Constants.R_HEADS) || refName
								.startsWith(Constants.R_REMOTES));

				if (!canConfirmOnTag())
					confirmationBtn.setEnabled(branchSelected);
				else
					confirmationBtn.setEnabled(branchSelected || tagSelected);

				if (renameButton != null) {
					renameButton.setEnabled(branchSelected && !tagSelected
							&& !tagSelected);
				}

				if (newButton != null) {
					newButton.setEnabled(branchSelected && !tagSelected);
				}
			}
		});

		branchTree.addOpenListener(new IOpenListener() {

			public void open(OpenEvent event) {
				RepositoryTreeNode node = (RepositoryTreeNode) ((IStructuredSelection) branchTree
						.getSelection()).getFirstElement();
				if (node.getType() != RepositoryTreeNodeType.REF
						&& node.getType() != RepositoryTreeNodeType.TAG)
					branchTree.setExpandedState(node, !branchTree
							.getExpandedState(node));
				else if (confirmationBtn.isEnabled())
					okPressed();

			}
		});

		createCustomArea(parent);

		String rawTitle = getTitle();
		String title = NLS.bind(rawTitle, new Object[] { repo.getDirectory() });

		setTitle(title);
		setMessage(getMessageText());
		getShell().setText(title);

		applyDialogFont(parent);

		return parent;
