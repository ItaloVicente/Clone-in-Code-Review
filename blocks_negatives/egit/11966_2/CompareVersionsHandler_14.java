				final String gitPath = getRepoRelativePath(repo, fileInput);

				final ITypedElement base = CompareUtils
						.getFileRevisionTypedElement(gitPath, commit1, repo);
				final ITypedElement next = CompareUtils
						.getFileRevisionTypedElement(gitPath, commit2, repo);
				final ITypedElement ancestor = CompareUtils.
						getFileRevisionTypedElementForCommonAncestor(
						gitPath, commit1, commit2, repo);
				CompareEditorInput in = new GitCompareFileRevisionEditorInput(
						base, next, ancestor, null);
				openInCompare(event, in);
