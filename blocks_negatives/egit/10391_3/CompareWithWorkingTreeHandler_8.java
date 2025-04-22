				final String gitPath = getRepoRelativePath(repo, file);
				ITypedElement left = CompareUtils.getFileRevisionTypedElement(
						gitPath, leftCommit, repo);
				ITypedElement right = CompareUtils.getFileRevisionTypedElement(
						gitPath, commit, repo);
				final ITypedElement ancestor = CompareUtils.
						getFileRevisionTypedElementForCommonAncestor(
						gitPath, headCommit, commit, repo);
				final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
						left, right, ancestor, null);
				openInCompare(event, in);
				return null;
