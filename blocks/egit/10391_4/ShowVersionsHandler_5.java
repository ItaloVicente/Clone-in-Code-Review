		} else if (input instanceof File) {
			File fileInput = (File) input;
			gitPath = getRepoRelativePath(repository, fileInput);
		} else {
			return null;
		}

		boolean errorOccurred = false;
		List<ObjectId> ids = new ArrayList<ObjectId>();

		Iterator<?> it = selection.iterator();
		while (it.hasNext()) {
			RevCommit commit = (RevCommit) it.next();
			IFileRevision revision = null;
			try {
				revision = CompareUtils.getFileRevision(gitPath, commit,
						repository, null);
			} catch (IOException e) {
				Activator.logError(NLS.bind(
						UIText.GitHistoryPage_errorLookingUpPath, gitPath,
						commit.getId()), e);
				errorOccurred = true;
			}

			if (revision == null)
				ids.add(commit.getId());
			else if (compareMode) {
