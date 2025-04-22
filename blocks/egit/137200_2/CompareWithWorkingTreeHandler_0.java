			if (repo != null) {
				final String leftCommitPath = getRepoRelativePath(repo, file);
				final String rightCommitPath = getRenamedPath(leftCommitPath,
						commit);
				ITypedElement right = CompareUtils.getFileRevisionTypedElement(
						rightCommitPath, commit, repo);
				CompareEditorInput in = new GitCompareFileRevisionEditorInput(
						new LocalNonWorkspaceTypedElement(repo,
								new Path(file.getAbsolutePath())),
						right, null);
				CompareUtils.openInCompare(workbenchPage, in);
