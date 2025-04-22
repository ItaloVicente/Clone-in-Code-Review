			final IFile baseFile = (IFile) resource;

			final ITypedElement base = new FileRevisionTypedElement(
					new LocalFileRevision(baseFile));

			final ITypedElement next;
			try {
				next = getElementForRef(mapping.getRepository(), mapping
						.getRepoRelativePath(baseFile), dlg.getRefName());
			} catch (IOException e) {
				Activator.handleError(
						UIText.CompareWithIndexAction_errorOnAddToIndex, e,
						true);
				return null;
