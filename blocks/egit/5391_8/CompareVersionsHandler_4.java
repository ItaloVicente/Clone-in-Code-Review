				IFile resource = (IFile) input;
				final RepositoryMapping map = RepositoryMapping
						.getMapping(resource);
				final String gitPath = map.getRepoRelativePath(resource);
				final String commit1Path = getRenamedPath(gitPath, commit1);
				final String commit2Path = getRenamedPath(gitPath, commit2);
				CompareUtils.openInCompare(commit1, commit2, commit1Path,
						commit2Path, map.getRepository(), workBenchPage);
