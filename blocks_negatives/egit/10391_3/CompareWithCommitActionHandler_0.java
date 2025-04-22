				RepositoryMapping mapping = RepositoryMapping
						.getMapping(resources[0]);
				next = getElementForCommit(mapping.getRepository(), mapping
						.getRepoRelativePath(baseFile), dlg.getCommitId());

				ancestor =  CompareUtils.getFileRevisionTypedElementForCommonAncestor(mapping
						.getRepoRelativePath(baseFile), repo.resolve(Constants.HEAD),
						dlg.getCommitId(), repo);
