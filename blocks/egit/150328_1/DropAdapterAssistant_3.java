	private IStatus validateRepositoryGroupNodeDrop() {
		ISelection selection = LocalSelectionTransfer.getTransfer()
				.getSelection();
		if (onlyRepositoryNodesSelected(selection)) {
			return Status.OK_STATUS;
		} else {
			return Status.CANCEL_STATUS;
		}
	}

	private IStatus handleRepositoryGroupNodeDrop(RepositoryGroupNode group,
			DropTargetEvent event) {
		if (event.data instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) event.data;
			if (onlyRepositoryNodesSelected(selection)) {
				List<String> reposToAdd = new ArrayList<>();
				for (Object treeNode : selection.toList()) {
					RepositoryNode repo = (RepositoryNode) treeNode;
					reposToAdd.add(
							repo.getRepository().getDirectory().toString());
				}
				RepositoryGroups groups = new RepositoryGroups();
				groups.addRepositoriesToGroup(group.getGroup().getUuid(),
						reposToAdd);

				refreshRepositoriesView();
				return Status.OK_STATUS;
			}
		}
		return Status.CANCEL_STATUS;
	}

	private IStatus handleWOrkspaceRootDrop(DropTargetEvent event) {
		if (event.data instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) event.data;
			if (onlyRepositoryNodesSelected(selection)) {
				List<String> reposToRemove = new ArrayList<>();
				for (Object treeNode : selection.toList()) {
					RepositoryNode repo = (RepositoryNode) treeNode;
					reposToRemove.add(
							repo.getRepository().getDirectory().toString());
				}
				RepositoryGroups groups = new RepositoryGroups();
				groups.removeFromGroups(reposToRemove);
				refreshRepositoriesView();
				return Status.OK_STATUS;
			}
		}
		return Status.CANCEL_STATUS;
	}

	private void refreshRepositoriesView() {
		RepositoriesView view = (RepositoriesView) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart();
		view.refresh();
	}

	private boolean onlyRepositoryNodesSelected(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			List<?> list = ((IStructuredSelection) selection).toList();
			for (Object element : list) {
				if (!(element instanceof RepositoryNode)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

