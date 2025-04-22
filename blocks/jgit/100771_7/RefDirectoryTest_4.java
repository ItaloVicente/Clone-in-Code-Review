	public void testBatchRefUpdateUpdateToMissingObjectAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master"
		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.REJECTED_MISSING_OBJECT
				commands.get(0).getResult());
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
		assertEquals("[HEAD
				.toString());
		assertEquals(A.getId()
	}

	@Test
	public void testBatchRefUpdateAddMissingObjectNonAtomic() throws IOException {
