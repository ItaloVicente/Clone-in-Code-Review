		if (aTarget instanceof RepositoryGroupNode) {
			return handleRepositoryGroupNodeDrop((RepositoryGroupNode) aTarget,
					aDropTargetEvent);
		} else if (aTarget instanceof IWorkspaceRoot) {
			return handleWorkspaceRootDrop(aDropTargetEvent);
		}
