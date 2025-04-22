		try {
			IStructuredSelection selection = getSelection(null);
			if (selection.size() != 1)
				return false;
			RevCommit commit = (RevCommit) selection.getFirstElement();
			return (commit.getParentCount() == 1);
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
