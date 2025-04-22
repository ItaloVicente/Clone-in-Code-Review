			treeWalk.setRecursive(true);
			if (tree != null)
				treeWalk.addTree(tree);
			else
				treeWalk.addTree(new EmptyTreeIterator());
			treeWalk.addTree(new DirCacheIterator(repo.readDirCache()));
			treeWalk.setFilter(PathFilterGroup
					.createFromStrings(Collections.singleton(Repository
							.stripWorkDir(repo.getWorkTree(), file))));
			return treeWalk.next();
		}
