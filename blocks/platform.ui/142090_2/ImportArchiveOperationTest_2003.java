		openTestWindow().run(true, true, operation);
		operation.setOverwriteResources(true);
		openTestWindow().run(true, true, operation);
		verifyFiles(directoryNames.length, false);
	}

	public void testZipSetOverwriteResources() throws Exception {
		setup(ARCHIVE_SOURCE_PROPERTY);
		project = FileUtil.createProject("ImporZiprSetOverwriteResources");
		ZipFile zipFile = new ZipFile(zipFileURL.getPath());
		ZipLeveledStructureProvider structureProvider = new ZipLeveledStructureProvider(zipFile);
		zipFile = new ZipFile(zipFileURL.getPath());
