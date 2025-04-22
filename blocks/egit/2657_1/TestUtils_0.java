
	public void assertRepositoryContainsFiles(Repository repository, String[] paths) throws Exception {
		Set<String> expectedfiles = new HashSet<String>();
		for (String path:paths)
			expectedfiles.add(path);
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(repository.resolve("HEAD^{tree}"));
		treeWalk.setRecursive(true);
		while (treeWalk.next()) {
			String path = treeWalk.getPathString();
			if (!expectedfiles.contains(path))
				fail("Repository contains unexpected expected file " + path);
			expectedfiles.remove(path);
		}
		if (expectedfiles.size()>0) {
			StringBuilder message = new StringBuilder("Repository does not contain expected files: ");
			for (String path: expectedfiles) {
				message.append(path);
				message.append(" ");
			}
			fail(message.toString());
		}
	}

	public void assertRepositoryContainsFilesWithContent(Repository repository,
			String... args) throws Exception {
		HashMap<String, String> expectedfiles = mkmap(args);
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(repository.resolve("HEAD^{tree}"));
		treeWalk.setRecursive(true);
		while (treeWalk.next()) {
			String path = treeWalk.getPathString();
			assertTrue(expectedfiles.containsKey(path));
			ObjectId objectId = treeWalk.getObjectId(0);
			byte[] expectedContent = expectedfiles.get(path).getBytes();
			byte[] repoContent = treeWalk.getObjectReader().open(objectId)
					.getBytes();
			if (!Arrays.equals(repoContent, expectedContent)) {
				fail("File " + path + " has repository content "
						+ new String(repoContent)
						+ " instead of expected content "
						+ new String(expectedContent));
			}
			expectedfiles.remove(path);
		}
		if (expectedfiles.size() > 0) {
			StringBuilder message = new StringBuilder(
					"Repository does not contain expected files: ");
			for (String path : expectedfiles.keySet()) {
				message.append(path);
				message.append(" ");
			}
			fail(message.toString());
		}
	}

	private static HashMap<String, String> mkmap(String... args) {
		if ((args.length % 2) > 0)
			throw new IllegalArgumentException("needs to be pairs");
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < args.length; i += 2) {
			map.put(args[i], args[i+1]);
		}
		return map;
	}


