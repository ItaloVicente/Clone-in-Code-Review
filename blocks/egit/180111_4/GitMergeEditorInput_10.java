	private LocalResourceTypedElement createWithHiddenResource(URI uri,
			String name, IFile file, Charset encoding) throws IOException {
		IFile tmp = createHiddenResource(uri, name, encoding);
		return new HiddenResourceTypedElement(tmp, file);
	}

