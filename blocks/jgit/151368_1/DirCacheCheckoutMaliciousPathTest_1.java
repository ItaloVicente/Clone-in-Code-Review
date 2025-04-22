			ObjectId firstCommitId;
			try (ObjectInserter newObjectInserter = git.getRepository()
					.newObjectInserter()) {
				CommitBuilder commitBuilder = new CommitBuilder();
				commitBuilder.setAuthor(author);
				commitBuilder.setCommitter(committer);
				commitBuilder.setMessage("foo#1");
				commitBuilder.setTreeId(insertId);
				firstCommitId = newObjectInserter.insert(commitBuilder);
