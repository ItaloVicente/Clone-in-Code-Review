	private LocalResourceTypedElement createWithHiddenResource(URI uri,
			String name, IFile file, Charset encoding) throws IOException {
		IFile tmp = createHiddenResource(uri, name, encoding);
		return new HiddenResourceTypedElement(tmp, file);
	}

	private IFile createHiddenResource(URI uri, String name, Charset encoding)
			throws IOException {
		try {
			IFile tmp = HiddenResources.INSTANCE.createFile(uri, name, encoding,
					null);
			if (toDelete == null) {
				toDelete = new ArrayList<>();
			}
			toDelete.add(tmp);
			return tmp;
		} catch (CoreException e) {
			throw new IOException(e.getMessage(), e);
		}
	}

