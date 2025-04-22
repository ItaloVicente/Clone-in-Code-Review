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
				if (compareMode) {
					ITypedElement right = CompareUtils
							.getFileRevisionTypedElement(gitPath, commit, map
									.getRepository());
					final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
							SaveableCompareEditorInput
									.createFileElement(resource), right, null);
					try {
						openInCompare(event, in);
					} catch (Exception e) {
						errorOccured = true;
