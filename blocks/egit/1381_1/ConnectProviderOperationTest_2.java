
		ObjectId id;
		ObjectInserter inserter = thisGit.newObjectInserter();
		try {
			entryA.setId(inserter.insert(Constants.OBJ_BLOB, srcA.getBytes("UTF-8")));
			srcTree.setId(inserter.insert(Constants.OBJ_TREE, srcTree.format()));
			prjTree.setId(inserter.insert(Constants.OBJ_TREE, prjTree.format()));
			rootTree.setId(inserter.insert(Constants.OBJ_TREE, rootTree.format()));
			CommitBuilder commit = new CommitBuilder();
			commit.setTreeId(rootTree.getTreeId());
			commit.setAuthor(new PersonIdent("J. Git", "j.git@egit.org",
					new Date(60876075600000L), TimeZone.getTimeZone("GMT+1")));
			commit.setCommitter(commit.getAuthor());
			commit.setMessage("testNewUnsharedFile\n\nJunit tests\n");
			id = inserter.insert(commit);
			inserter.flush();
		} finally {
			inserter.release();
		}

