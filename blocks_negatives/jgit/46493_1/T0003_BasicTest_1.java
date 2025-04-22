	@Test
	public void test021_createTreeTag() throws IOException {
		final ObjectId emptyId = insertEmptyBlob();
		final Tree almostEmptyTree = new Tree(db);
		almostEmptyTree.addEntry(new FileTreeEntry(almostEmptyTree, emptyId,
				"empty".getBytes(), false));
		final ObjectId almostEmptyTreeId = insertTree(almostEmptyTree);
		final TagBuilder t = new TagBuilder();
		t.setObjectId(almostEmptyTreeId, Constants.OBJ_TREE);
		t.setTag("test021");
		t.setTagger(new PersonIdent(author, 1154236443000L, -4 * 60));
		t.setMessage("test021 tagged\n");
		ObjectId actid = insertTag(t);
		assertEquals("b0517bc8dbe2096b419d42424cd7030733f4abe5", actid.name());

		RevTag mapTag = parseTag(actid);
		assertEquals(Constants.OBJ_TREE, mapTag.getObject().getType());
		assertEquals("test021 tagged\n", mapTag.getFullMessage());
		assertEquals(new PersonIdent(author, 1154236443000L, -4 * 60), mapTag
				.getTaggerIdent());
		assertEquals("417c01c8795a35b8e835113a85a5c0c1c77f67fb", mapTag
				.getObject().getId().name());
	}

	@Test
	public void test022_createCommitTag() throws IOException {
		final ObjectId emptyId = insertEmptyBlob();
		final Tree almostEmptyTree = new Tree(db);
		almostEmptyTree.addEntry(new FileTreeEntry(almostEmptyTree, emptyId,
				"empty".getBytes(), false));
		final ObjectId almostEmptyTreeId = insertTree(almostEmptyTree);
		final CommitBuilder almostEmptyCommit = new CommitBuilder();
		almostEmptyCommit.setAuthor(new PersonIdent(author, 1154236443000L,
		almostEmptyCommit.setCommitter(new PersonIdent(author, 1154236443000L,
				-2 * 60));
		almostEmptyCommit.setMessage("test022\n");
		almostEmptyCommit.setTreeId(almostEmptyTreeId);
		ObjectId almostEmptyCommitId = insertCommit(almostEmptyCommit);
		final TagBuilder t = new TagBuilder();
		t.setObjectId(almostEmptyCommitId, Constants.OBJ_COMMIT);
		t.setTag("test022");
		t.setTagger(new PersonIdent(author, 1154236443000L, -4 * 60));
		t.setMessage("test022 tagged\n");
		ObjectId actid = insertTag(t);
		assertEquals("0ce2ebdb36076ef0b38adbe077a07d43b43e3807", actid.name());

		RevTag mapTag = parseTag(actid);
		assertEquals(Constants.OBJ_COMMIT, mapTag.getObject().getType());
		assertEquals("test022 tagged\n", mapTag.getFullMessage());
		assertEquals(new PersonIdent(author, 1154236443000L, -4 * 60), mapTag
				.getTaggerIdent());
		assertEquals("b5d3b45a96b340441f5abb9080411705c51cc86c", mapTag
				.getObject().getId().name());
	}

	@Test
	public void test023_createCommitNonAnullii() throws IOException {
		final ObjectId emptyId = insertEmptyBlob();
		final Tree almostEmptyTree = new Tree(db);
		almostEmptyTree.addEntry(new FileTreeEntry(almostEmptyTree, emptyId,
				"empty".getBytes(), false));
		final ObjectId almostEmptyTreeId = insertTree(almostEmptyTree);
		CommitBuilder commit = new CommitBuilder();
		commit.setTreeId(almostEmptyTreeId);
		commit.setAuthor(new PersonIdent("Joe H\u00e4cker", "joe@example.com",
				4294967295000L, 60));
		commit.setCommitter(new PersonIdent("Joe Hacker", "joe2@example.com",
				4294967295000L, 60));
		commit.setEncoding("UTF-8");
		commit.setMessage("\u00dcbergeeks");
		ObjectId cid = insertCommit(commit);
		assertEquals("4680908112778718f37e686cbebcc912730b3154", cid.name());

		RevCommit loadedCommit = parseCommit(cid);
		assertEquals(commit.getMessage(), loadedCommit.getFullMessage());
	}

	@Test
	public void test024_createCommitNonAscii() throws IOException {
		final ObjectId emptyId = insertEmptyBlob();
		final Tree almostEmptyTree = new Tree(db);
		almostEmptyTree.addEntry(new FileTreeEntry(almostEmptyTree, emptyId,
				"empty".getBytes(), false));
		final ObjectId almostEmptyTreeId = insertTree(almostEmptyTree);
		CommitBuilder commit = new CommitBuilder();
		commit.setTreeId(almostEmptyTreeId);
		commit.setAuthor(new PersonIdent("Joe H\u00e4cker", "joe@example.com",
				4294967295000L, 60));
		commit.setCommitter(new PersonIdent("Joe Hacker", "joe2@example.com",
				4294967295000L, 60));
		commit.setEncoding("ISO-8859-1");
		commit.setMessage("\u00dcbergeeks");
		ObjectId cid = insertCommit(commit);
		assertEquals("2979b39d385014b33287054b87f77bcb3ecb5ebf", cid.name());
	}

