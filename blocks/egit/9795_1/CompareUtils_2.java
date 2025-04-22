	public static ITypedElement getFileTypedElement(IPath location) {
		IFile file = ResourceUtil.getFileForLocation(location);
		if (file != null)
			return SaveableCompareEditorInput.createFileElement(file);
		else
			return new LocalNonWorkspaceTypedElement(location);
	}

