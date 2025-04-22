	@Test
	public void testInteractiveRebaseSquashFixupSequence() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit1").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit2").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("#commit3").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("@commit4").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage(";commit5").call();

		StoredConfig config = git.getRepository().getConfig();
		config.setString("core"
		RebaseResult result = git.rebase().setUpstream("HEAD~4")
				.runInteractively(new InteractiveHandler2() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(0).setAction(Action.PICK);
							steps.get(1).setAction(Action.SQUASH);
							steps.get(2).setAction(Action.FIXUP);
							steps.get(3).setAction(Action.SQUASH);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						fail("should not be called");
						return commit;
					}

					@Override
					public ModifyResult editCommitMessage(String message
							CleanupMode mode
						assertEquals('@'
						assertEquals("@ This is a combination of 4 commits.\n"
								+ "@ The first commit's message is:\n"
								+ "commit2\n"
								+ "@ This is the 2nd commit message:\n"
								+ "#commit3\n"
								+ "@ The 3rd commit message will be skipped:\n"
								+ "@ @commit4\n"
								+ "@ This is the 4th commit message:\n"
								+ ";commit5"
						return new ModifyResult() {

							@Override
							public String getMessage() {
								return message;
							}

							@Override
							public CleanupMode getCleanupMode() {
								return mode;
							}

							@Override
							public boolean shouldAddChangeId() {
								return false;
							}
						};
					}
				}).call();
		assertEquals(Status.OK
		Iterator<RevCommit> logIterator = git.log().all().call().iterator();
		String actualCommitMsg = logIterator.next().getFullMessage();
		assertEquals("commit2\n#commit3\n;commit5"
	}

