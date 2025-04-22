		inserter = thisGit.newObjectInserter();

		tree = new Tree(thisGit);
		Tree projectTree = tree.addTree("Project-1");
		File project1_a_txt = createFile("Project-1/A.txt",A.txt - first version\n);
		addFile(projectTree,project1_a_txt);
		projectTree.setId(inserter.insert(Constants.OBJ_TREE, projectTree.format()));
		File project1_b_txt = createFile("Project-1/B.txt",B.txt - first version\n);
		addFile(projectTree,project1_b_txt);
		projectTree.setId(inserter.insert(Constants.OBJ_TREE, projectTree.format()));
		tree.setId(inserter.insert(Constants.OBJ_TREE, tree.format()));
		CommitBuilder commit = new CommitBuilder();
		commit.setAuthor(new PersonIdent(jauthor, new Date(0L), TimeZone
				.getTimeZone("GMT+1")));
		commit.setCommitter(new PersonIdent(jcommitter, new Date(0L), TimeZone
				.getTimeZone("GMT+1")));
		commit.setMessage("Foo\n\nMessage");
		commit.setTreeId(tree.getTreeId());
		ObjectId commitId = inserter.insert(commit);

		tree = new Tree(thisGit);
		projectTree = tree.addTree("Project-1");
		addFile(projectTree,project1_a_txt);

		File project1_b_v2_txt = createFile("Project-1/B.txt",B.txt - second version\n);
		addFile(projectTree,project1_b_v2_txt);
		projectTree.setId(inserter.insert(Constants.OBJ_TREE, projectTree.format()));
		tree.setId(inserter.insert(Constants.OBJ_TREE, tree.format()));
		commit = new CommitBuilder();
		commit.setAuthor(new PersonIdent(jauthor, new Date(0L), TimeZone
				.getTimeZone("GMT+1")));
		commit.setCommitter(new PersonIdent(jcommitter, new Date(0L), TimeZone
				.getTimeZone("GMT+1")));
		commit.setMessage("Modified");
		commit.setParentId(commitId);
		commit.setTreeId(tree.getTreeId());
		commitId = inserter.insert(commit);
		inserter.flush();

		RefUpdate lck = thisGit.updateRef("refs/heads/master");
		assertNotNull("obtained lock", lck);
		lck.setNewObjectId(commitId);
		assertEquals(RefUpdate.Result.NEW, lck.forceUpdate());
