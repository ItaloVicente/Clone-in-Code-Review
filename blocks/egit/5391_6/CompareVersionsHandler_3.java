				IFile resource = (IFile) input;
				final RepositoryMapping map = RepositoryMapping
						.getMapping(resource);
				final String gitPath = map.getRepoRelativePath(resource);
				final String commit1Path = getRenamedPath(gitPath, commit1);
				final String commit2Path = getRenamedPath(gitPath, commit2);

				final ITypedElement base = CompareUtils
						.getFileRevisionTypedElement(commit1Path, commit1,
								map.getRepository());
				final ITypedElement next = CompareUtils
						.getFileRevisionTypedElement(commit2Path, commit2,
								map.getRepository());
				CompareEditorInput in = new GitCompareFileRevisionEditorInput(
						base, next, null);
				CompareUtils.openInCompare(workBenchPage, in);
