				final ITypedElement base = CompareUtils
						.getFileRevisionTypedElement(gitPath, commit1, map
								.getRepository());
				final ITypedElement next = CompareUtils
						.getFileRevisionTypedElement(gitPath, commit2, map
								.getRepository());
				CompareEditorInput in = new GitCompareFileRevisionEditorInput(
						base, next, null);
				CompareUtils.openInCompare(workBenchPage, in);
