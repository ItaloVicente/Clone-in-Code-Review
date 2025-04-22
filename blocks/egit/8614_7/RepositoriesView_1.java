	public ShowInContext getShowInContext() {
		IStructuredSelection selection = (IStructuredSelection) getCommonViewer()
				.getSelection();
		List<Object> elements = getShowInElements(selection);
		HistoryPageInput historyPageInput = getHistoryPageInput(selection);
		return new ShowInContext(historyPageInput, new StructuredSelection(elements));
	}

	public String[] getShowInTargetIds() {
		IStructuredSelection selection = (IStructuredSelection) getCommonViewer()
				.getSelection();
		for (Object element : selection.toList())
			if (element instanceof RepositoryNode)
				return new String[] { ReflogView.VIEW_ID };

		return new String[] {};
	}

	private static List<Object> getShowInElements(IStructuredSelection selection) {
		List<Object> elements = new ArrayList<Object>();
		for (Object element : selection.toList()) {
			if (element instanceof FileNode || element instanceof FolderNode
					|| element instanceof WorkingDirNode) {
				RepositoryTreeNode treeNode = (RepositoryTreeNode) element;
				IPath path = treeNode.getPath();
				IResource resource = ResourceUtil.getResourceForLocation(path);
				if (resource != null)
					elements.add(resource);
			} else if (element instanceof RepositoryNode) {
				elements.add(element);
			} else if (element instanceof RepositoryNode
					|| element instanceof RemoteNode
					|| element instanceof FetchNode
					|| element instanceof PushNode
					|| element instanceof RefNode) {
				elements.add(element);
			}
		}
		return elements;
	}

	private static HistoryPageInput getHistoryPageInput(IStructuredSelection selection) {
		List<File> files = new ArrayList<File>();
		Repository repo = null;
		for (Object element : selection.toList()) {
			Repository nodeRepository;
			if (element instanceof FileNode) {
				FileNode fileNode = (FileNode) element;
				files.add(fileNode.getObject());
				nodeRepository = fileNode.getRepository();
			} else if (element instanceof FolderNode) {
				FolderNode folderNode = (FolderNode) element;
				files.add(folderNode.getObject());
				nodeRepository = folderNode.getRepository();
			} else {
				return null;
			}
			if (repo == null)
				repo = nodeRepository;
			if (repo != nodeRepository)
				return null;
		}
		if (repo != null)
			return new HistoryPageInput(repo, files.toArray(new File[files.size()]));
		else
			return null;
	}

