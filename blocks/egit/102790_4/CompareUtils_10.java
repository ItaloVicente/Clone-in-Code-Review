				EolStreamType streamType = null;
				String command = null;
				try (TreeWalk walk = new TreeWalk(repository)) {
					walk.setOperationType(OperationType.CHECKIN_OP);
					walk.addTree(new DirCacheIterator(cache));
					FileTreeIterator files = new FileTreeIterator(repository);
					files.setDirCacheIterator(walk, 0);
					walk.addTree(files);
					walk.setFilter(AndTreeFilter.create(
							PathFilterGroup.createFromStrings(gitPath),
							new NotIgnoredFilter(1)));
					walk.setRecursive(true);
					if (walk.next()) {
						streamType = walk.getEolStreamType();
						command = walk.getFilterCommand(
								Constants.ATTR_FILTER_TYPE_CLEAN);
					}
