		try (TestRepository<?> tr = new TestRepository<>(db)) {
			RevWalk rw = tr.getRevWalk();
			RevTree tree = rw.parseTree(treeish);
			RevObject obj = tr.get(tree
			if (obj == null) {
				return null;
			}
			return new String(
					rw.getObjectReader().open(obj
