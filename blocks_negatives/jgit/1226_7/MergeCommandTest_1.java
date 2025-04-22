
	private void addNewFileToIndex(String filename) throws IOException,
			CorruptObjectException {
		File writeTrashFile = writeTrashFile(filename, filename);

		GitIndex index = db.getIndex();
		Entry entry = index.add(db.getWorkTree(), writeTrashFile);
		entry.update(writeTrashFile);
		index.write();
	}
