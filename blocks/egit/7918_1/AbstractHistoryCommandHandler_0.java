	protected List<RevCommit> getSelectedCommits() throws ExecutionException {
		List<RevCommit> commits = new ArrayList<RevCommit>();
		GitHistoryPage page = getPage();
		if (page == null)
			return Collections.emptyList();
		IStructuredSelection selection = getSelection(page);
		HistoryPageInput input = page.getInputInternal();
		if (input == null)
			return Collections.emptyList();
		RevWalk walk = new RevWalk(input.getRepository());
		try {
			for (Object element : selection.toList()) {
				RevCommit commit = (RevCommit) element;
				RevCommit reparsed = walk.parseCommit(commit.getId());
				commits.add(reparsed);
			}
		} catch (IOException e) {
			throw new ExecutionException(e.getMessage(), e);
		} finally {
			walk.release();
		}
		return commits;
	}

