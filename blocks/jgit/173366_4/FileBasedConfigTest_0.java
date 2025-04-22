
	private Path parent(Path file) {
		Path parent = file.getParent();
		assertNotNull(parent);
		return parent;
	}
