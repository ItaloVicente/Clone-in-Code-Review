				final ITypedElement base = SaveableCompareEditorInput
						.createFileElement(baseFile);

				final ITypedElement next;
				ITypedElement ancestor = null;
				try {
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(resources[0]);
					next = getElementForRef(mapping.getRepository(), mapping
							.getRepoRelativePath(baseFile), dlg.getRefName());
					ancestor = CompareUtils.getFileRevisionTypedElementForCommonAncestor(
							mapping.getRepoRelativePath(baseFile), repo.resolve(Constants.HEAD),
							repo.resolve(dlg.getRefName()), repo);
				} catch (IOException e) {
					Activator.handleError(
							UIText.CompareWithIndexAction_errorOnAddToIndex, e,
							true);
					return null;
