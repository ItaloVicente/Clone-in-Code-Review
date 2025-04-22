		if (!branchesToDelete.isEmpty()) {
			try {
				DeleteBranchOperationUI.deleteBranches(repository,
						branchesToDelete);
			} catch (InvocationTargetException e) {
				throw new ExecutionException(
						UIText.RepositoriesView_BranchDeletionFailureMessage,
						e.getCause());
