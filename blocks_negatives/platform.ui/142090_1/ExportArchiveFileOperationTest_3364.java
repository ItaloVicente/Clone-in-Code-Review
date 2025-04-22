    		verifyFile(file);
    	}
    }

    private void verifyFile(String entryName){
    	for (String fileName : fileNames) {
    		boolean dotProjectFileShouldBePresent = ".project".equals(entryName) && !flattenPaths && !excludeProjectPath;
    		if (fileName.equals(entryName) || dotProjectFileShouldBePresent) {
