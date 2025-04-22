
	@SuppressWarnings("nls")
	public void convertToReftable(boolean writeLogs) throws IOException {
		File newRefs = new File(getDirectory()

		FileReftableDatabase.convertFrom(this

		File refsFile = new File(getDirectory()

		FileUtils.rename(refsFile
		FileUtils.rename(newRefs
		refs.close();
		refs = new FileReftableDatabase(this
	}
