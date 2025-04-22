
	public void menuAboutToShow(IMenuManager manager) {
		manager.add(new Separator(ICommonMenuConstants.GROUP_OPEN));
		manager.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));

		IAction openAction = createOpenAction();
		if (openAction != null)
			manager.appendToGroup(ICommonMenuConstants.GROUP_OPEN, openAction);

		MenuManager showInSubMenu = new MenuManager(
				UIText.CompareTreeView_ShowIn_label);
		showInSubMenu.add(ContributionItemFactory.VIEWS_SHOW_IN
				.create(getSite().getWorkbenchWindow()));
		manager.appendToGroup(ICommonMenuConstants.GROUP_OPEN, showInSubMenu);
	}

	public ShowInContext getShowInContext() {
		IPath repoPath = getRepositoryPath();
		ITreeSelection selection = (ITreeSelection) tree.getSelection();
		List<IResource> resources = new ArrayList<IResource>();
		for (Iterator it = selection.iterator(); it.hasNext();) {
			Object obj = it.next();
			if (obj instanceof IResource)
				resources.add((IResource) obj);
			else if (obj instanceof PathNode && repoPath != null) {
				PathNode pathNode = (PathNode) obj;
				IResource resource = ResourceUtil
						.getResourceForLocation(repoPath.append(pathNode.path
								.toString()));
				if (resource != null)
					resources.add(resource);
			}
		}
		return new ShowInContext(null, new StructuredSelection(resources));
	}

	private void createContextMenu() {
		MenuManager manager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		manager.setRemoveAllWhenShown(true);
		manager.addMenuListener(this);

		Menu contextMenu = manager.createContextMenu(tree.getControl());
		tree.getControl().setMenu(contextMenu);
		getSite().registerContextMenu(manager, tree);
	}

	private void openFileInEditor(String filePath) {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		File file = new File(filePath);
		if (!file.exists()) {
			String message = NLS.bind(UIText.CommitFileDiffViewer_FileDoesNotExist, filePath);
			Activator.showError(message, null);
		}
		IWorkbenchPage page = window.getActivePage();
		EgitUiEditorUtils.openEditor(file, page);
	}

	private IAction createOpenAction() {
		ITreeSelection selection = (ITreeSelection) tree.getSelection();
		final List<String> pathsToOpen = getSelectedPaths(selection);
		if (pathsToOpen == null)
			return null;

		return new Action(
				UIText.CommitFileDiffViewer_OpenWorkingTreeVersionInEditorMenuLabel) {
			@Override
			public void run() {
				for (String filePath: pathsToOpen)
					openFileInEditor(filePath);
				}
		};
	}

	private List<String> getSelectedPaths(ITreeSelection selection) {
		IPath repoPath = getRepositoryPath();
		List<String> pathsToOpen = new ArrayList<String>();
		for (Iterator it = selection.iterator(); it.hasNext();) {
			Object obj = it.next();
			if (obj instanceof IFile) {
				pathsToOpen.add(((IFile) obj).getLocation().toOSString());
			} else if (obj instanceof PathNode && repoPath != null) {
				PathNode pathNode = (PathNode) obj;
				if (pathNode.type == PathNode.Type.FOLDER
						|| pathNode.type == PathNode.Type.FILE_DELETED)
					return null;
				pathsToOpen.add(repoPath.append(pathNode.path).toOSString());
			} else {
				return null; // fail if one of the selected elements does not fit
			}
		}
		return pathsToOpen;
	}

	private IPath getRepositoryPath() {
		Repository repo = null;
		if (repositoryMapping != null)
			repo = repositoryMapping.getRepository();
		else if (input instanceof Repository)
			repo = (Repository) input;

		if (repo != null)
			return new Path(repo.getWorkTree().getAbsolutePath());

		return null;
	}

