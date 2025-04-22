	@Override
	public OutputStream call() throws GitAPIException {
		final Format<?> fmt = lookupFormat(format);
		return writeArchive(fmt);
        }

