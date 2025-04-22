	@Test
	public void testBatchRefUpdateUpdateToMissingObject() throws IOException {
		writeLooseRef("refs/heads/master"
		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(false);
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.REJECTED_MISSING_OBJECT
				commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.OK
		assertEquals("[HEAD
				.toString());
		assertEquals(A.getId()
		assertEquals(B.getId()
	}

	@Test
	public void testBatchRefUpdateAddMissingObject() throws IOException {
		writeLooseRef("refs/heads/master"
		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.REJECTED_MISSING_OBJECT
				commands.get(1).getResult());
		assertEquals("[HEAD
				.toString());
		assertEquals(B.getId()
	}

