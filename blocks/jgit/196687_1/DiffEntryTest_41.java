			try (TreeWalk walk = new TreeWalk(db)) {
				walk.addTree(new EmptyTreeIterator());
				walk.addTree(new EmptyTreeIterator());
				walk.addTree(new EmptyTreeIterator());
				DiffEntry.scan(walk);
			}
		});
