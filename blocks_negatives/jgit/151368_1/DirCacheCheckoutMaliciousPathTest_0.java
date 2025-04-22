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
				treeFormatter.append(path[i].getBytes(UTF_8), 0,
						path[i].getBytes(UTF_8).length,
							mode, insertId, true);
				insertId = newObjectInserter.insert(treeFormatter);
				mode = FileMode.TREE;
