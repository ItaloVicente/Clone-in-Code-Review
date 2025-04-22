
	protected RevCommit getSelectedCommit(ExecutionEvent event, Repository repo)
			throws ExecutionException {
		ReflogEntry entry = (ReflogEntry) ((IStructuredSelection) HandlerUtil
				.getCurrentSelectionChecked(event)).getFirstElement();
		if (entry == null)
			return null;

		RevCommit commit = null;
		RevWalk w = new RevWalk(repo);
		try {
			commit = w.parseCommit(entry.getNewId());
		} catch (IOException e) {
			throw new ExecutionException(e.getMessage(), e);
		} finally {
			w.dispose();
		}
		return commit;
	}
