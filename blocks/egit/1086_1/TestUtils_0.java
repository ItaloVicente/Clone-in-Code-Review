		if (firstProject.exists()) {
			firstProject.delete(true, null);
		}
		File testFile = new File(parentFile, projectName);
		if (testFile.exists())
			deleteRecursive(testFile);

