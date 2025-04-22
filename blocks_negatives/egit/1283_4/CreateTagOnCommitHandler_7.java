		try {
			IStructuredSelection sel = getSelection(null);
			return sel.size() == 1
					&& sel.getFirstElement() instanceof RevCommit;
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
