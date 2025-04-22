			commitBuilder = new CommitBuilder();
			commitBuilder.setAuthor(author);
			commitBuilder.setCommitter(committer);
			commitBuilder.setMessage("foo#2");
			commitBuilder.setTreeId(insertId);
			commitBuilder.setParentId(firstCommitId);
			ObjectId commitId = newObjectInserter.insert(commitBuilder);

