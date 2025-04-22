		Git git = new Git(db);
		ObjectInserter newObjectInserter;
		newObjectInserter = git.getRepository().newObjectInserter();
		ObjectId blobId = newObjectInserter.insert(Constants.OBJ_BLOB,
				"data".getBytes());
		newObjectInserter = git.getRepository().newObjectInserter();
		FileMode mode = FileMode.REGULAR_FILE;
		ObjectId insertId = blobId;
		for (int i = path.length - 1; i >= 0; --i) {
			TreeFormatter treeFormatter = new TreeFormatter();
			treeFormatter.append("goodpath", mode, insertId);
			insertId = newObjectInserter.insert(treeFormatter);
			mode = FileMode.TREE;
		}
		newObjectInserter = git.getRepository().newObjectInserter();
		CommitBuilder commitBuilder = new CommitBuilder();
		commitBuilder.setAuthor(author);
		commitBuilder.setCommitter(committer);
		commitBuilder.setMessage("foo#1");
		commitBuilder.setTreeId(insertId);
		ObjectId firstCommitId = newObjectInserter.insert(commitBuilder);

		newObjectInserter = git.getRepository().newObjectInserter();
		mode = FileMode.REGULAR_FILE;
		insertId = blobId;
		for (int i = path.length - 1; i >= 0; --i) {
			TreeFormatter treeFormatter = new TreeFormatter();
			treeFormatter.append(path[i], mode, insertId);
			insertId = newObjectInserter.insert(treeFormatter);
			mode = FileMode.TREE;
		}
