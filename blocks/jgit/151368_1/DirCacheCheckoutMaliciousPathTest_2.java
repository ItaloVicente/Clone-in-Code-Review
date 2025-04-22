			ObjectId commitId;
			try (ObjectInserter newObjectInserter = git.getRepository()
					.newObjectInserter()) {
				mode = FileMode.REGULAR_FILE;
				insertId = blobId;
				for (int i = path.length - 1; i >= 0; --i) {
					TreeFormatter treeFormatter = new TreeFormatter();
					treeFormatter.append(path[i].getBytes(UTF_8)
							path[i].getBytes(UTF_8).length
							true);
					insertId = newObjectInserter.insert(treeFormatter);
					mode = FileMode.TREE;
				}
