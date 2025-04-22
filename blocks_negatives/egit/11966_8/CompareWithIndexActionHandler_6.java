
	private ITypedElement getBaseTypeElement(final IPath baseLocation) {
		IFile file = ResourceUtil.getFileForLocation(baseLocation);
		if (file != null)
			return SaveableCompareEditorInput.createFileElement(file);
		else
			return new LocalNonWorkspaceTypedElement(baseLocation);
	}

	private ITypedElement getIndexTypedElement(final IPath location) throws IOException {
		IFile file = ResourceUtil.getFileForLocation(location);
		if (file != null)
			return CompareUtils.getIndexTypedElement(file);
		else {
			RepositoryMapping mapping = RepositoryMapping.getMapping(location);
			if (mapping != null)
				return CompareUtils.getIndexTypedElement(mapping.getRepository(), mapping.getRepoRelativePath(location));
			else
				return null;
		}
	}
