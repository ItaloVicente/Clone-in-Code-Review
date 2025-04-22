    protected void createTestFile() throws CoreException {
        if (testFile == null) {
            createTestFolder();
            testFile = testFolder.getFile("Foo.txt");
            testFile.create(
                    new ByteArrayInputStream("Some content.".getBytes()),
                    false, null);
        }
    }
