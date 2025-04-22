	public ShowInContext getShowInContext() {
		IStructuredSelection selection = (IStructuredSelection) getCommonViewer()
				.getSelection();
		List<IResource> resources = getResources(selection);
		HistoryPageInput historyPageInput = getHistoryPageInput(selection);
		return new ShowInContext(historyPageInput, new StructuredSelection(resources));
	}

	private static List<IResource> getResources(IStructuredSelection selection) {
		List<IResource> resources = new ArrayList<IResource>();
		for (Object element : selection.toList()) {
			if (element instanceof FileNode || element instanceof FolderNode
					|| element instanceof WorkingDirNode) {
				RepositoryTreeNode treeNode = (RepositoryTreeNode) element;
				IPath path = treeNode.getPath();
				IResource resource = ResourceUtil.getResourceForLocation(path);
				if (resource != null)
					resources.add(resource);
			}
		}
		return resources;
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

