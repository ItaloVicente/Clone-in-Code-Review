	public void testBatchRefUpdateFileDirectoryConflictAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate
				.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				commands.get(0).getResult());
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(2)));
		assertEquals("[HEAD
				.keySet().toString());
		assertEquals(A.getId()
		assertEquals(B.getId()
	}

	@Test
	public void testBatchRefUpdateConflictThanksToDeleteNonAtomic()
			throws IOException {
