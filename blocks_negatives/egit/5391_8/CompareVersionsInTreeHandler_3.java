				final ITypedElement base = CompareUtils
						.getFileRevisionTypedElement(gitPath, commit1, repo);
				final ITypedElement next = CompareUtils
						.getFileRevisionTypedElement(gitPath, commit2, repo);
				CompareEditorInput in = new GitCompareFileRevisionEditorInput(
						base, next, null);
				CompareUtils.openInCompare(workBenchPage, in);
