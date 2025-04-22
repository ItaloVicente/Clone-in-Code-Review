	@Nullable
	private static Repository getRepository(IURIEditorInput uriInput) {
		File file = getFile(uriInput);
		if (file == null) {
			return null;
		}
		Path path = new Path(file.getAbsolutePath());
		RepositoryMapping mapping = RepositoryMapping.getMapping(path);
		if (mapping != null) {
			return mapping.getRepository();
		}
		Repository repository = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache().getRepository(path);
		return repository;
	}

	@Nullable
	private static File getFile(IURIEditorInput uriInput) {
		URI uri = uriInput.getURI();
		if (uri == null) {
			return null;
		}
		try {
			return new File(uri);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

