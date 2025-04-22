
	@Override
	public PathMatcher getPathMatcher(String globPattern) {
		return new PathMatcher_Java7(globPattern);
	}

	@Override
	public void copyFile(File sourceFile
		Files.copy(sourceFile.toPath()
				StandardCopyOption.REPLACE_EXISTING);
	}
