	public GitSelectWizardPage(Repository repository, String path) {
		super(GitSelectWizardPage.class.getName());
		setTitle(UIText.GitImportWithDirectoriesPage_PageTitle);
		setMessage(UIText.GitImportWithDirectoriesPage_PageMessage);
		initialRepository = repository;
		initialPath = path;
	}

	public String getPath() {
		IStructuredSelection sel = (IStructuredSelection) tv.getSelection();
		RepositoryTreeNode node = (RepositoryTreeNode) sel.getFirstElement();
		if (node != null && node.getType() == RepositoryTreeNodeType.FOLDER)
			return ((File) node.getObject()).getPath();
		if (node != null && node.getType() == RepositoryTreeNodeType.WORKINGDIR)
			return node.getRepository().getWorkDir().getPath();
		return null;
	}

	public void setRepository(Repository repo) {
		List<WorkingDirNode> input = new ArrayList<WorkingDirNode>();
		if (repo != null)
			input.add(new WorkingDirNode(null, repo));
		tv.setInput(input);
		tv.setSelection(new StructuredSelection(input.get(0)));
