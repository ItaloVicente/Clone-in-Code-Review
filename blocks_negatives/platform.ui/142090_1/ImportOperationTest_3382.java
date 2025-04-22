        importElements.add(element);
        ImportOperation operation = new ImportOperation(project.getFullPath(),
                FileSystemStructureProvider.INSTANCE, this, importElements);
        openTestWindow().run(true, true, operation);

        verifyFiles(directoryNames.length);
    }

    public void testImportSource() throws Exception {
        project = FileUtil.createProject("ImportSource");
        ImportOperation operation = new ImportOperation(project.getFullPath(),
                new File(localDirectory), FileSystemStructureProvider.INSTANCE,
                this);
        openTestWindow().run(true, true, operation);
        verifyFiles(directoryNames.length);
    }

    public void testImportSourceList() throws Exception {
        project = FileUtil.createProject("ImportSourceList");
        File element = new File(localDirectory + File.separator
                + directoryNames[0]);
