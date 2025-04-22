
	@Override
	public String getCharset() throws CoreException {
		if (charset == null) {
			charset = CompareCoreUtils.getResourceEncoding(db, path);
		}
		return charset;
	}
