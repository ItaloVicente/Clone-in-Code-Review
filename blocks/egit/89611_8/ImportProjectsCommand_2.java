		openWizard(event, selectedNodes);
		return null;
	}

	private void openWizard(ExecutionEvent event,
			List<RepositoryTreeNode> selectedNodes) throws ExecutionException {
		IWizardDescriptor descriptor = findSmartImportWizardDescriptor();
		if (descriptor == null || selectedNodes.size() > 1) {
			RepositoryTreeNode node;
			if (selectedNodes.size() > 0) {
				node = findRepoNode(selectedNodes.get(0));
			} else {
				node = selectedNodes.get(0);
			}
			String path = getPathFromNode(node);
			if (path == null) {
				return;
			}
			openGitCreateProjectViaWizardWizard(event, node, path,
					getPaths(selectedNodes));
		}
		else {
			String path = getPathFromNode(selectedNodes.get(0));
			openSmartImportWizard(event, descriptor, path);
		}
	}

	private List<String> getPaths(List<RepositoryTreeNode> pSelectedNodes) {
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
