	private void addFile(Tree t,File f) throws IOException {
		ObjectId id;
		FileInputStream in = new FileInputStream(f);
		try {
			id = inserter.insert(Constants.OBJ_BLOB, in.getChannel().size(), in);
		} finally {
			in.close();
		}
		t.addEntry(new FileTreeEntry(t,id,f.getName().getBytes("UTF-8"),false));
	}

