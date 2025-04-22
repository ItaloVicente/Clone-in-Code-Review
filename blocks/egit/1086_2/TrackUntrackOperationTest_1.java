	private void assertTrackedState(IFile[] fileArr, boolean expectedState)
			throws IOException {
		DirCache cache = DirCache.read(repository1.getRepository());
		for (IFile file : fileArr) {
			RepositoryMapping rm = RepositoryMapping.getMapping(file);
			String fileDir = rm.getRepoRelativePath(file);
			boolean tracked = cache.findEntry(fileDir) > -1;
			assertTrue("Wrong tracking state", tracked == expectedState);
		}
