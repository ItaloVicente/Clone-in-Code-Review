	private void show(final AnyObjectId id
			throws IOException {
		outw.print(id.name());
		outw.print('\t');
		outw.print(name);
		outw.println();
