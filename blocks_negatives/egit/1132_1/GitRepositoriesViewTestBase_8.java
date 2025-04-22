
	protected static Repository lookupRepository(File directory)
			throws Exception {
		return org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache().lookupRepository(directory);
	}

	protected static void touchAndSubmit() throws Exception {
		IProject prj = ResourcesPlugin.getWorkspace().getRoot().getProject(
				PROJ1);
		if (!prj.isAccessible())
			throw new IllegalStateException("No project to touch");
		IFile file = prj.getFile(new Path("folder/test.txt"));
		String newContent = "Touched at " + System.currentTimeMillis();
		file.setContents(new ByteArrayInputStream(newContent.getBytes(prj
				.getDefaultCharset())), 0, null);

		IFile[] commitables = new IFile[] { file };
		ArrayList<IFile> untracked = new ArrayList<IFile>();
		untracked.addAll(Arrays.asList(commitables));
		CommitOperation op = new CommitOperation(commitables,
				new ArrayList<IFile>(), untracked,
				"Test Author <test.author@test.com>",
				"Test Committer <test.commiter@test.com>", newContent);
		op.execute(null);
	}

	protected SWTBotTreeItem getLocalBranchesItem(SWTBotTree tree, File repo)
			throws Exception {
		Repository repository = lookupRepository(repo);
		RepositoryNode root = new RepositoryNode(null, repository);
		BranchesNode branches = new BranchesNode(root, repository);
		LocalBranchesNode localBranches = new LocalBranchesNode(branches,
				repository);

		String rootText = labelProvider.getText(root);
		SWTBotTreeItem rootItem = tree.getTreeItem(rootText);
		SWTBotTreeItem branchesItem = rootItem.expand().getNode(
				labelProvider.getText(branches));
		SWTBotTreeItem localItem = branchesItem.expand().getNode(
				labelProvider.getText(localBranches));
		return localItem;
	}

	protected SWTBotTreeItem getRemoteBranchesItem(SWTBotTree tree,
			File repositoryFile) throws Exception {
		Repository repository = lookupRepository(repositoryFile);
		RepositoryNode root = new RepositoryNode(null, repository);
		BranchesNode branches = new BranchesNode(root, repository);
		RemoteBranchesNode remoteBranches = new RemoteBranchesNode(branches,
				repository);

		String rootText = labelProvider.getText(root);
		SWTBotTreeItem rootItem = tree.getTreeItem(rootText);
		SWTBotTreeItem branchesItem = rootItem.expand().getNode(
				labelProvider.getText(branches));
		SWTBotTreeItem remoteItem = branchesItem.expand().getNode(
				labelProvider.getText(remoteBranches));
		return remoteItem;
	}

	protected SWTBotTreeItem getWorkdirItem(SWTBotTree tree, File repositoryFile)
			throws Exception {
		Repository repository = lookupRepository(repositoryFile);
		RepositoryNode root = new RepositoryNode(null, repository);

		WorkingDirNode workdir = new WorkingDirNode(root, repository);

		String rootText = labelProvider.getText(root);
		SWTBotTreeItem rootItem = tree.getTreeItem(rootText);
		SWTBotTreeItem workdirItem = rootItem.expand().getNode(
				labelProvider.getText(workdir));
		return workdirItem;
	}

	protected SWTBotTreeItem getRootItem(SWTBotTree tree, File repositoryFile)
			throws Exception {
		Repository repository = lookupRepository(repositoryFile);
		RepositoryNode root = new RepositoryNode(null, repository);
		String rootText = labelProvider.getText(root);
		SWTBotTreeItem rootItem = tree.getTreeItem(rootText);
		return rootItem;
	}

	protected SWTBotTreeItem getSymbolicRefsItem(SWTBotTree tree,
			File repositoryFile) throws Exception {
		Repository repository = lookupRepository(repositoryFile);
		RepositoryNode root = new RepositoryNode(null, repository);
		SymbolicRefsNode symrefsnode = new SymbolicRefsNode(root, repository);
		SWTBotTreeItem rootItem = tree.getTreeItem(labelProvider.getText(root))
				.expand();
		SWTBotTreeItem symrefsitem = rootItem.getNode(labelProvider
				.getText(symrefsnode));
		return symrefsitem;
	}

	protected SWTBotTreeItem getRemotesItem(SWTBotTree tree, File repositoryFile)
			throws Exception {
		Repository repository = lookupRepository(repositoryFile);
		RepositoryNode root = new RepositoryNode(null, repository);
		RemotesNode remotes = new RemotesNode(root, repository);

		String rootText = labelProvider.getText(root);
		SWTBotTreeItem rootItem = tree.getTreeItem(rootText);
		SWTBotTreeItem remotesItem = rootItem.expand().getNode(
				labelProvider.getText(remotes));
		return remotesItem;
	}

	protected SWTBotTreeItem getTagsItem(SWTBotTree tree, File repositoryFile)
			throws Exception {
		Repository repository = lookupRepository(repositoryFile);
		RepositoryNode root = new RepositoryNode(null, repository);
		TagsNode tags = new TagsNode(root, repository);

		String rootText = labelProvider.getText(root);
		SWTBotTreeItem rootItem = tree.getTreeItem(rootText);
		SWTBotTreeItem tagsItem = rootItem.expand().getNode(
				labelProvider.getText(tags));
		return tagsItem;
	}

	protected String getTestFileContent() throws Exception {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFile(new Path("folder/test.txt"));
		if (file.exists()) {
			byte[] bytes = new byte[0];
			InputStream is = null;
			try {
				is = file.getContents();
				bytes = new byte[is.available()];
				is.read(bytes);
			} finally {
				if (is != null)
					is.close();
			}
			return new String(bytes, file.getCharset());
		} else {
			return "";
		}
	}

	/**
	 * @param projectExplorerTree
	 * @param project
	 *            name of a project
	 * @return the project item pertaining to the project
	 */
	protected SWTBotTreeItem getProjectItem(SWTBotTree projectExplorerTree,
			String project) {
		for (SWTBotTreeItem item : projectExplorerTree.getAllItems()) {
			String itemText = item.getText();
			StringTokenizer tok = new StringTokenizer(itemText, " ");
			String name = tok.nextToken();
			if (project.equals(name))
				return item;
		}
		return null;
	}

	protected void pressAltAndChar(SWTBotShell shell, char charToPress) {
		Display display = Display.getDefault();
		Event evt = new Event();
		evt.type = SWT.KeyDown;
		evt.item = shell.widget;
		evt.keyCode = SWT.ALT;
		display.post(evt);
		evt.keyCode = 0;
		evt.character = charToPress;
		display.post(evt);
		evt.type = SWT.KeyUp;
		display.post(evt);
		evt.keyCode = SWT.ALT;
		evt.character = ' ';
		display.post(evt);
	}

	/**
	 * @param shell
	 * @param itemWithShortcut
	 *            ALT + the char right after '&' will be pressed
	 */
	protected void activateItemByKeyboard(SWTBotShell shell,
			String itemWithShortcut) {
		int index = itemWithShortcut.indexOf('&');
		if (index >= 0 && index < itemWithShortcut.length())
			pressAltAndChar(shell, itemWithShortcut.charAt(index + 1));
	}

