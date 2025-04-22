	@Test
	public void testListDetached() throws Exception {
		RefUpdate updateRef = db.updateRef(Constants.HEAD
		updateRef.setNewObjectId(db.resolve("6fd41be"));
		updateRef.update();
		assertEquals("* (no branch) 6fd41be initial commit"
				execute("git branch -v")[0]);
	}

	@Test
	public void testListContains() throws Exception {
		new Git(db).branchCreate().setName("initial").call();
		RevCommit second = new Git(db).commit().setMessage("second commit")
				.call();
		assertArrayOfLinesEquals(new String[] { "  initial"
				execute("git branch --contains 6fd41be"));
		assertArrayOfLinesEquals(new String[] { "* master"
				execute("git branch --contains " + second.name()));
	}

