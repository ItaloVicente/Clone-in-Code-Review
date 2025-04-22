				ITypedElement ancestor = null;
				try {
					RevCommit commonAncestor = RevUtils.getCommonAncestor(repo, repo.resolve(Constants.HEAD), repo.resolve(dlg.getRefName()));
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(resources[0]);
					ancestor =  CompareUtils.getFileRevisionTypedElement(mapping
							.getRepoRelativePath(baseFile), commonAncestor, mapping.getRepository());
				} catch (IOException e) {
					Activator.logError(NLS.bind(UIText.CompareWithWorkingTreeHandler_errorCommonAncestor,
							Constants.HEAD, dlg.getRefName()), e);
				}
