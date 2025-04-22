	@Override
	public OutputStream call() throws GitAPIException {
		final Format<?> fmt = formats.get(format);
		return writeArchive(fmt);
        }

