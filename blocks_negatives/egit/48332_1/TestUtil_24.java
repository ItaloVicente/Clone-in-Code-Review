		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(repository.resolve("HEAD^{tree}"));
		treeWalk.setRecursive(true);
		while (treeWalk.next()) {
			String path = treeWalk.getPathString();
			assertTrue(expectedfiles.containsKey(path));
			ObjectId objectId = treeWalk.getObjectId(0);
			byte[] expectedContent = expectedfiles.get(path).getBytes("UTF-8");
			byte[] repoContent = treeWalk.getObjectReader().open(objectId)
					.getBytes();
			if (!Arrays.equals(repoContent, expectedContent))
				fail("File " + path + " has repository content "
						+ new String(repoContent, "UTF-8")
						+ " instead of expected content "
						+ new String(expectedContent, "UTF-8"));
			expectedfiles.remove(path);
