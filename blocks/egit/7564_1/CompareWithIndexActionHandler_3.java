	private ITypedElement getHeadTypedElement(final IPath location) throws IOException {
		IFile file = ResourceUtil.getFileForLocation(location);
		if (file != null)
			return CompareUtils.getHeadTypedElement(file);
		else
			return CompareUtils.getHeadTypedElement(location, null);
	}
