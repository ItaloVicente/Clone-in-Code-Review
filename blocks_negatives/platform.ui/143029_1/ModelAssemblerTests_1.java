		IExtension[] extensions = createExtensions(filePath);
		assembler.runProcessors(extensions, initial, afterFragments);
	}

	/**
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private IExtension[] createExtensions(String filePath) throws IOException {
