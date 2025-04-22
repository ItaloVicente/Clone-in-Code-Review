			} else {
				File fileInput = getLocalFileInput(event);
				if (fileInput != null) {
					Repository repo = getRepository(event);
					final String gitPath = getRepoRelativePath(repo, fileInput);

					final ITypedElement base = CompareUtils
							.getFileRevisionTypedElement(gitPath, commit1, repo);
					final ITypedElement next = CompareUtils
							.getFileRevisionTypedElement(gitPath, commit2, repo);
					CompareEditorInput in = new GitCompareFileRevisionEditorInput(
							base, next, null);
					openInCompare(event, in);
				}

