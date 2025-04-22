		openWizard(event, selectedNodes);
		return null;
	}

	private void openWizard(ExecutionEvent event,
			List<RepositoryTreeNode> selectedNodes) throws ExecutionException {
		IWizardDescriptor descriptor = findSmartImportWizardDescriptor();
		if (descriptor == null || multipleProjectsSelected(selectedNodes)) {
			RepositoryTreeNode node;
			if (multipleProjectsSelected(selectedNodes)) {
				node = findRepoNode(selectedNodes.get(0));
			} else {
				node = selectedNodes.get(0);
			}
			String path = getPathFromNode(node);
			if (path == null) {
				return;
			}
			openGitCreateProjectViaWizardWizard(event, node, path,
					getMultipleSelectedProjects(selectedNodes));
		} else {
			String path = getPathFromNode(selectedNodes.get(0));
			openSmartImportWizard(event, descriptor, path);
		}
	}

	private boolean multipleProjectsSelected(List<?> selectedNodes) {
		return selectedNodes.size() > 1;
	}

	private List<String> getMultipleSelectedProjects(List<RepositoryTreeNode> pSelectedNodes) {
		if (!multipleProjectsSelected(pSelectedNodes)) {
			return Collections.emptyList();
		}
		ArrayList<String> paths = new ArrayList<>();
		for (RepositoryTreeNode node : pSelectedNodes) {
			String path = getPathFromNode(node);
			if (path == null) {
				return null;
			}
			paths.add(path);
		}
		return paths;
	}

	private RepositoryTreeNode findRepoNode(RepositoryTreeNode pNode) {
		RepositoryTreeNode result = pNode;
		while (!result.getType().equals(RepositoryTreeNodeType.REPO)) {
			result = result.getParent();
		}
		return result;
	}

	private String getPathFromNode(RepositoryTreeNode node) {
