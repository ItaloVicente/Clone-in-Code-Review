		openWizard(event, selectedNodes);
		return null;
	}

	private void openWizard(ExecutionEvent pEvent,
			List<RepositoryTreeNode> pSelectedNodes) throws ExecutionException {
		IWizardDescriptor descriptor = findSmartImportWizardDescriptor();
		if (descriptor == null || pSelectedNodes.size() > 1) {
			RepositoryTreeNode node;
			if (pSelectedNodes.size() > 0) {
				node = findRepoNode(pSelectedNodes.get(0));
			} else {
				node = pSelectedNodes.get(0);
			}
			String path = getPathFromNode(node);
			if (path == null) {
				return;
			}
			openGitCreateProjectViaWizardWizard(pEvent, node, path,
					getPaths(pSelectedNodes));
		}
		else {
			String path = getPathFromNode(pSelectedNodes.get(0));
			openSmartImportWizard(pEvent, descriptor, path);
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
