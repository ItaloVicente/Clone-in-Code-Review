			try (TreeWalk walk = new TreeWalk(db)) {
				walk.addTree(new EmptyTreeIterator());
				walk.addTree(new EmptyTreeIterator());
				walk.setRecursive(true);
				DiffEntry.scan(walk
			}
		});
