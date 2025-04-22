    private void createFile(String parentName, String filePath)
            throws IOException {
        String newFilePath = parentName + File.separatorChar + filePath;
        File newFile = new File(newFilePath);
        newFile.createNewFile();
    }
