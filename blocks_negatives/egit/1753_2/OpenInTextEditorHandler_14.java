		while (it.hasNext()) {
			RevCommit commit = (RevCommit) it.next();
			IFileRevision rev = null;
			try {
				rev = CompareUtils.getFileRevision(gitPath, commit, map
						.getRepository(), null);
			} catch (IOException e) {
				Activator.logError(NLS.bind(
						UIText.GitHistoryPage_errorLookingUpPath, gitPath,
						commit.getId()), e);
				errorOccured = true;
			}
			if (rev != null) {
