		final File root = index.getRepository().getWorkTree();
		new IndexTreeWalker(index, tree, root, new AbstractIndexTreeVisitor() {
			public void visitEntry(TreeEntry treeEntry, Entry indexEntry, File file) {
				if (treeEntry == null) {
					added.add(indexEntry.getName());
					anyChanges = true;
				} else if (indexEntry == null) {
					if (!(treeEntry instanceof Tree))
						removed.add(treeEntry.getFullName());
					anyChanges = true;
