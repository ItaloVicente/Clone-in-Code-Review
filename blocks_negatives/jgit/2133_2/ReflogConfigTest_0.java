	private void addFileToTree(final Tree t, String filename, String content)
			throws IOException {
		FileTreeEntry f = t.addFile(filename);
		writeTrashFile(f.getName(), content);
		t.accept(new WriteTree(trash, db), TreeEntry.MODIFIED_ONLY);
	}

	private void commit(final Tree t, String commitMsg, PersonIdent author,
