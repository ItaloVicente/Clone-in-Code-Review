		String encoding = CompareCoreUtils.getResourceEncoding(baseFile);
		return getHeadTypedElement(baseFile.getLocation(), encoding);
	}

	public static ITypedElement getHeadTypedElement(IPath location,
			String encoding) throws IOException {
		final RepositoryMapping mapping = RepositoryMapping.getMapping(location);
