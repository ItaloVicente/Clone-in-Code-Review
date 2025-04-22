        importElements.add(element);
        operation.setFilesToImport(importElements);
        openTestWindow().run(true, true, operation);
        verifyFiles(importElements.size());
    }

    public void testSetOverwriteResources() throws Exception {
        project = FileUtil.createProject("ImportSetOverwriteResources");
        File element = new File(localDirectory);
