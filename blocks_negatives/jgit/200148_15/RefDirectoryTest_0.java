	private void writeLooseRef(String name, AnyObjectId id) throws IOException {
		writeLooseRef(name, id.name() + "\n");
	}

	private void writeLooseRef(String name, String content) throws IOException {
		write(new File(diskRepo.getDirectory(), name), content);
	}

	private void writePackedRef(String name, AnyObjectId id) throws IOException {
