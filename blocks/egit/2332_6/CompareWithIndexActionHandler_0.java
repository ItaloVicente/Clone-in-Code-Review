		final IResource[] resources = getSelectedResources(event);

		if (resources.length == 1 && resources[0] instanceof IFile) {
			final IFile baseFile = (IFile) resources[0];
			final ITypedElement base = SaveableCompareEditorInput
					.createFileElement(baseFile);

			final ITypedElement next;
			try {
				next = getHeadTypedElement(baseFile);
			} catch (IOException e) {
				Activator.handleError(
						UIText.CompareWithIndexAction_errorOnAddToIndex, e,
						true);
				return null;
			}
