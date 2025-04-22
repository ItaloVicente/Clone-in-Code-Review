	/** {@inheritDoc} */
	@Deprecated
	@Override
	public void putEntry(ArchiveOutputStream out,
			String path, FileMode mode, ObjectLoader loader)
			throws IOException {
		putEntry(out, null, path, mode,loader);
	}

