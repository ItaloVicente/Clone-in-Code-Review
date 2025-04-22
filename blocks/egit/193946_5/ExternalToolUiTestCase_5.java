		for (java.nio.file.Path createdFile : createdFiles) {
			Files.deleteIfExists(createdFile);
		}
	}

	protected void clearResultFile() throws Exception {
		Files.write(resultFile, new byte[] {});
