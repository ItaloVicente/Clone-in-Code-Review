	@Test
	public void testRebaseWithAutoStashAndSubdirs() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_REBASE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSTASH
		writeTrashFile("sub/file0"
		git.add().addFilepattern("sub/file0").call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("sub/file0"

		Set<String> modifiedFiles = new HashSet<>();
		ListenerHandle handle = db.getListenerList()
				.addWorkingTreeModifiedListener(
						event -> modifiedFiles.addAll(event.getModified()));
		try {
			assertEquals(Status.OK
					.setUpstream("refs/heads/master").call().getStatus());
		} finally {
			handle.remove();
		}
		checkFile(new File(new File(db.getWorkTree()
				"unstaged modified file0");
		checkFile(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
		assertEquals(
				"[file1
						+ "[file2
						+ "[sub/file0
				indexState(CONTENT));
		assertEquals(RepositoryState.SAFE
		List<String> modified = new ArrayList<>(modifiedFiles);
		Collections.sort(modified);
		assertEquals("[file1
	}

