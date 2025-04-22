	public void testBatchRefUpdateConflictThanksToDeleteAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE)
				new ReceiveCommand(B
						ReceiveCommand.Type.DELETE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.OK
		assertEquals("[HEAD
				.keySet().toString());
		assertEquals(A.getId()
	}

	@Test
	public void testBatchRefUpdateUpdateToMissingObjectNonAtomic() throws IOException {
