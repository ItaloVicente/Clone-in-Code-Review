		ObjectWriter writer = new ObjectWriter(thisGit);
		entryA.setId(writer.writeBlob(fileA.getRawLocation().toFile()));
		srcTree.setId(writer.writeTree(srcTree));
		prjTree.setId(writer.writeTree(prjTree));
		rootTree.setId(writer.writeTree(rootTree));
		CommitBuilder commit = new CommitBuilder();
		commit.setTreeId(rootTree.getTreeId());
		commit.setAuthor(new PersonIdent("J. Git", "j.git@egit.org", new Date(
				60876075600000L), TimeZone.getTimeZone("GMT+1")));
		commit.setCommitter(commit.getAuthor());
		commit.setMessage("testNewUnsharedFile\n\nJunit tests\n");
		ObjectId id = writer.writeCommit(commit);
