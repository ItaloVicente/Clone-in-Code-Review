	public void addToIndex(IFile file) throws IOException {
		GitIndex index = repository.getIndex();
		Entry entry = index.getEntry(getRepoRelativePath(file.getLocation()
				.toOSString()));
		assertNotNull(entry);
		if (entry.isModified(repository.getWorkTree()))
			entry.update(new File(repository.getWorkTree(), entry.getName()));
		index.write();
