				if (rev != null) {
					try {
						EgitUiEditorUtils.openTextEditor(getPart(event)
								.getSite().getPage(), rev, null);
					} catch (CoreException e) {
						Activator.logError(e.getMessage(), e);
						errorOccured = true;
					}
				} else {
					ids.add(commit.getId());
				}
			}
		} else {
			File fileInput = getLocalFileInput(event);
			if (fileInput != null) {
				Repository repo = getRepository(event);
				gitPath = getRepoRelativePath(repo, fileInput);
				Iterator<?> it = selection.iterator();
				while (it.hasNext()) {
					RevCommit commit = (RevCommit) it.next();
					IFileRevision rev = null;
					try {
						rev = CompareUtils.getFileRevision(gitPath, commit,
								repo, null);
					} catch (IOException e) {
						Activator.logError(NLS.bind(
								UIText.GitHistoryPage_errorLookingUpPath,
								gitPath, commit.getId()), e);
						errorOccured = true;
					}
					if (rev != null) {
						try {
							EgitUiEditorUtils.openTextEditor(getPart(event)
									.getSite().getPage(), rev, null);
						} catch (CoreException e) {
							Activator.logError(e.getMessage(), e);
							errorOccured = true;
						}
					} else {
						ids.add(commit.getId());
					}
				}
