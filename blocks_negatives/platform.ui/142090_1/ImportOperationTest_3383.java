        importElements.add(element);
        ImportOperation operation = new ImportOperation(project.getFullPath(),
                new File(localDirectory), FileSystemStructureProvider.INSTANCE,
                this, importElements);
        openTestWindow().run(true, true, operation);
        verifyFiles(importElements.size());
    }

    public void testSetContext() throws Exception {
        project = FileUtil.createProject("ImportSetContext");
        File element = new File(localDirectory);
