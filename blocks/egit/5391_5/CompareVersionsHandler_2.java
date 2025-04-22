				repo = getRepository(event);
				final String gitPath = getRepoRelativePath(repo, fileInput);
				final String commit1Path = getRenamedPath(gitPath, commit1);
				final String commit2Path = getRenamedPath(gitPath, commit2);

				final ITypedElement base = CompareUtils
						.getFileRevisionTypedElement(commit1Path, commit1, repo);
				final ITypedElement next = CompareUtils
						.getFileRevisionTypedElement(commit2Path, commit2, repo);
				CompareEditorInput in = new GitCompareFileRevisionEditorInput(
						base, next, null);
				CompareUtils.openInCompare(workBenchPage, in);
