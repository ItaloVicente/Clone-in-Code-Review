	@Test
	public void testUpdate_RefusesOrigHeadOnBare() throws IOException {
		assertTrue(refdb.getRepository().isBare());
		ObjectId txnId = getTxnCommitted();

		RefUpdate orig = refdb.newUpdate(ORIG_HEAD
		orig.setNewObjectId(B);
		assertEquals(RefUpdate.Result.LOCK_FAILURE
		assertEquals(txnId

		ReceiveCommand cmd = command(null
		BatchRefUpdate batch = refdb.newBatchUpdate();
		batch.addCommand(cmd);
		batch.execute(new RevWalk(repo)
		assertEquals(REJECTED_OTHER_REASON
		assertEquals(
				MessageFormat.format(JGitText.get().invalidRefName
				cmd.getMessage());
		assertEquals(txnId
	}

