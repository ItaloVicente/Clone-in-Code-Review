    public void testZipImport() throws Exception {
    	setup(ARCHIVE_SOURCE_PROPERTY);
        project = FileUtil.createProject("ImportZip");
        ZipFile zipFile = new ZipFile(zipFileURL.getPath());
        ZipLeveledStructureProvider structureProvider = new ZipLeveledStructureProvider(zipFile);
        zipFile = new ZipFile(zipFileURL.getPath());
