				if(canDirectlyOpenInCompare(file)) {
					final RepositoryMapping mapping = RepositoryMapping
							.getMapping(file.getProject());
					final String gitPath = mapping.getRepoRelativePath(file);
					ITypedElement right = CompareUtils.getFileRevisionTypedElement(
							gitPath, commit, mapping.getRepository());
					final ITypedElement ancestor = CompareUtils.
							getFileRevisionTypedElementForCommonAncestor(
							gitPath, headCommit, commit, repo);
