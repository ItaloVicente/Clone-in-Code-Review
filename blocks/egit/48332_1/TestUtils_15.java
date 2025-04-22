		try (TreeWalk treeWalk = new TreeWalk(repository)) {
			treeWalk.addTree(repository.resolve("HEAD^{tree}"));
			treeWalk.setRecursive(true);
			while (treeWalk.next()) {
				String path = treeWalk.getPathString();
				if (!expectedfiles.contains(path))
					fail("Repository contains unexpected expected file "
							+ path);
				expectedfiles.remove(path);
			}
