    /**
     * @param ext
     * @throws CoreException
     */
    private void createFiles(String ext) throws CoreException {
        for (int i = 0; i < 100; i++) {
            String fileName = i + "." + ext;
	        IFile iFile = testProject.getFile(fileName);
	        iFile.create(new ByteArrayInputStream(new byte[] { '\n' }), true, null);
        }
    }
