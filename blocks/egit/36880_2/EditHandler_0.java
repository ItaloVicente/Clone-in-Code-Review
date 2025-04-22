		Shell shell = getPart(event).getSite().getShell();

		editCommit(commit, repo, shell);
		return null;
	}

	public static boolean editCommit(RevCommit commit, Repository repo,
			Shell shell) {
