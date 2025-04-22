	@Test
	public void renameToExisting() throws Exception {
		assertNotNull(git.branchCreate().setName("foo").call());
		thrown.expect(RefAlreadyExistsException.class);
		git.branchRename().setOldName("master").setNewName("foo").call();
	}

	@Test
	public void renameToTag() throws Exception {
		Ref ref = git.tag().setName("foo").call();
		assertNotNull(ref);
		assertEquals("Unexpected tag name"
				ref.getName());
		ref = git.branchRename().setNewName("foo").call();
		assertNotNull(ref);
		assertEquals("Unexpected ref name"
				ref.getName());
		ref = git.branchRename().setOldName("foo").setNewName(Constants.MASTER)
				.call();
		assertNotNull(ref);
		assertEquals("Unexpected ref name"
				Constants.R_HEADS + Constants.MASTER
	}

	@Test
	public void renameToStupidName() throws Exception {
		Ref ref = git.branchRename().setNewName(Constants.R_HEADS + "foo")
				.call();
		assertEquals("Unexpected ref name"
				Constants.R_HEADS + Constants.R_HEADS + "foo"
				ref.getName());
		ref = git.branchRename().setNewName("foo").call();
		assertNotNull(ref);
		assertEquals("Unexpected ref name"
				ref.getName());
	}

