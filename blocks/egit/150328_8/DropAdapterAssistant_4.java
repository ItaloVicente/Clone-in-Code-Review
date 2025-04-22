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
				List<File> reposToAdd = new ArrayList<>();
				for (Object treeNode : selection.toList()) {
					RepositoryNode repo = (RepositoryNode) treeNode;
					reposToAdd.add(repo.getRepository().getDirectory());
				}
				RepositoryGroups.getInstance().addRepositoriesToGroup(
						group.getGroup().getGroupId(), reposToAdd);
				refreshRepositoriesView();
				return Status.OK_STATUS;
			}
		}
		return Status.CANCEL_STATUS;
	}

	private IStatus handleWorkspaceRootDrop(DropTargetEvent event) {
		if (event.data instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) event.data;
			if (onlyRepositoryNodesSelected(selection)) {
				List<File> reposToRemove = new ArrayList<>();
				for (Object treeNode : selection.toList()) {
					RepositoryNode repo = (RepositoryNode) treeNode;
					reposToRemove.add(repo.getRepository().getDirectory());
				}
				RepositoryGroups.getInstance().removeFromGroups(reposToRemove);
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
			return ((List<?>) ((IStructuredSelection) selection).toList())
					.stream().allMatch(e -> (e instanceof RepositoryNode));
		}
		return false;
	}

