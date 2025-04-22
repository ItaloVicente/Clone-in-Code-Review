				final ITypedElement base = SaveableCompareEditorInput
						.createFileElement(baseFile);

				final ITypedElement next;
				try {
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(resources[0]);
					next = getElementForRef(mapping.getRepository(), mapping
							.getRepoRelativePath(baseFile), dlg.getRefName());
				} catch (IOException e) {
					Activator.handleError(
							UIText.CompareWithIndexAction_errorOnAddToIndex, e,
							true);
					return null;
