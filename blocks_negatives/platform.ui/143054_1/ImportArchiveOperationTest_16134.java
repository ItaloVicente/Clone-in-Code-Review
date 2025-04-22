        openTestWindow().run(true, true, operation);
        operation.setOverwriteResources(true);
        openTestWindow().run(true, true, operation);
        closeZipFile(zipFile);
        verifyFiles(directoryNames.length, false);
    }

    public void testZipWithFileAtRoot() throws Exception {
    	setup(ARCHIVE_115800_PROPERTY);
        project = FileUtil.createProject("ImportZipWithFileAtRoot");
        ZipFile zipFile = new ZipFile(zipFileURL.getPath());
        ZipLeveledStructureProvider structureProvider = new ZipLeveledStructureProvider(zipFile);
        zipFile = new ZipFile(zipFileURL.getPath());
