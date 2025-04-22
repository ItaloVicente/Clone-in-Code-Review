		final IFile baseFile = (IFile) resource;
		final ITypedElement base = SaveableCompareEditorInput
				.createFileElement(baseFile);

		final ITypedElement next;
		try {
			next = getHeadTypedElement(baseFile);
		} catch (IOException e) {
			Activator.handleError(
					UIText.CompareWithIndexAction_errorOnAddToIndex, e, true);
			return null;
		}
