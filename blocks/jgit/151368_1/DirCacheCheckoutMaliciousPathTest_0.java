			try (ObjectInserter newObjectInserter = git.getRepository()
					.newObjectInserter()) {
				for (int i = path.length - 1; i >= 0; --i) {
					TreeFormatter treeFormatter = new TreeFormatter();
					treeFormatter.append("goodpath"
					insertId = newObjectInserter.insert(treeFormatter);
					mode = FileMode.TREE;
				}
