	private final Repository repository;

	JGitFileStore(final Repository repository) {
		this.repository = checkNotNull("repository"
	}

	@Override
	public String name() {
		return repository.getDirectory().getName();
	}

	@Override
	public String type() {
		return "file";
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	@Override
	public long getTotalSpace() throws IOException {
		return repository.getDirectory().getTotalSpace();
	}

	@Override
	public long getUsableSpace() throws IOException {
		return repository.getDirectory().getUsableSpace();
	}

	@Override
	public long getUnallocatedSpace() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean supportsFileAttributeView(final Class<? extends FileAttributeView> type) {
		checkNotNull("type"

		return type.equals(BasicFileAttributeView.class);
	}

	@Override
	public boolean supportsFileAttributeView(final String name) {
		checkNotEmpty("name"

		return name.equals("basic");
	}

	@Override
	public <V extends FileStoreAttributeView> V getFileStoreAttributeView(Class<V> type) {
		checkNotNull("type"

		return null;
	}

	@Override
	public Object getAttribute(final String attribute) throws UnsupportedOperationException
		checkNotEmpty("attribute"

		if (attribute.equals("totalSpace")) {
			return getTotalSpace();
		}
		if (attribute.equals("usableSpace")) {
			return getUsableSpace();
		}
		if (attribute.equals("readOnly")) {
			return isReadOnly();
		}
		if (attribute.equals("name")) {
			return name();
		}
		throw new UnsupportedOperationException("Attribute '" + attribute + "' not available");
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof FileStore)) {
			return false;
		}

		final FileStore ofs = (FileStore) o;

		return name().equals(ofs.name());
	}

	@Override
	public int hashCode() {
		return name().hashCode();
	}
