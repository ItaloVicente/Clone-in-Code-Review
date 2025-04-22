			gitPath = map.getRepoRelativePath(resource);
			Iterator<?> it = selection.iterator();
			while (it.hasNext()) {
				RevCommit commit = (RevCommit) it.next();
				String commitPath = getRenamedPath(gitPath, commit);
				IFileRevision rev = null;
				try {
					rev = CompareUtils.getFileRevision(commitPath, commit,
							map.getRepository(), null);
				} catch (IOException e) {
					Activator.logError(NLS.bind(
							UIText.GitHistoryPage_errorLookingUpPath,
							commitPath, commit.getId()), e);
					errorOccurred = true;
				}
				if (rev != null)
