				IFile resource = (IFile) input;
				final RepositoryMapping map = RepositoryMapping
						.getMapping(resource);
				final String gitPath = map.getRepoRelativePath(resource);

				final ITypedElement base = CompareUtils
						.getFileRevisionTypedElement(gitPath, commit1, map
								.getRepository());
				final ITypedElement next = CompareUtils
						.getFileRevisionTypedElement(gitPath, commit2, map
								.getRepository());
				final ITypedElement ancestor =
						CompareUtils.getFileRevisionTypedElementForCommonAncestor(
						gitPath, commit1, commit2, repo);
				CompareEditorInput in = new GitCompareFileRevisionEditorInput(
						base, next, ancestor, null);
				openInCompare(event, in);
