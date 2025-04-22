	private ITypedElement getBaseTypeElement(final IPath baseLocation) {
		IFile file = ResourceUtil.getFileForLocation(baseLocation);
		if (file != null)
			return SaveableCompareEditorInput.createFileElement(file);
		else
			return new LocalNonWorkspaceTypedElement(baseLocation.toOSString());
