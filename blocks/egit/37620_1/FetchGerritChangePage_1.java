	private void cherryPick(RevCommit commit, IProgressMonitor monitor)
			throws CoreException {
		monitor.setTaskName(UIText.FetchGerritChangePage_CherryPickingTaskName);
		List<RevCommit> commits = new ArrayList<RevCommit>();
		commits.add(commit);
		CherryPickOperation op = new CherryPickOperation(repository, commits);
		op.execute(monitor);

		monitor.worked(1);
	}

