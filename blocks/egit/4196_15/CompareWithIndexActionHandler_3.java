
	private ITypedElement getBaseTypeElement(final IFile baseFile) {
		final ITypedElement base;
		if (isNonWorkspace(baseFile)) {
			String path = baseFile.getFullPath().toOSString();
			base = new LocalNonWorkspaceTypedElement(path);
		} else
			base = SaveableCompareEditorInput
			.createFileElement(baseFile);
		return base;
	}

