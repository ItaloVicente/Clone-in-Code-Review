	public void openPack(final File pack, final File idx) throws IOException {
		objectDatabase.openPack(pack, idx);
	}

	public String toString() {
		return "Repository[" + getDirectory() + "]";
	}
