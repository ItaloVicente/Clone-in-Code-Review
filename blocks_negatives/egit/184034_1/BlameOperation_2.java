		if (revisionRuler instanceof IRevisionRulerColumnExtension)
			((IRevisionRulerColumnExtension) revisionRuler)
					.getRevisionSelectionProvider()
					.addSelectionChangedListener(
							new RevisionSelectionHandler(repository, path,
									storage));
