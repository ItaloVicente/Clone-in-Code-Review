    protected void createTestFolder() throws CoreException {
        if (testFolder == null) {
            createTestProject();
            testFolder = testProject.getFolder("TestFolder");
            testFolder.create(false, false, null);
        }
    }
