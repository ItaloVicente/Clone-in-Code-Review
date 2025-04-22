
	public void convertToReftable() throws IOException {
		File newRefs = new File(getDirectory()
		FileReftableDatabase newDb = FileReftableDatabase.convertFrom(this

		File refsFile = new File(getDirectory()

		FileUtils.rename(refsFile
		FileUtils.rename(newRefs
		newDb.close();
		refs.close();
		refs = new FileReftableDatabase(this
	}
