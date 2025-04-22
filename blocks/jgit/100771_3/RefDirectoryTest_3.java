	@Test
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
	public void testBatchRefUpdateOneNonExistentRefNonAtomic()
			throws IOException {
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
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.OK
		assertEquals("[refs/heads/foo2]"
		assertEquals(B.getId()
	}

	@Test
	public void testBatchRefUpdateOneNonExistentRefAtomic()
			throws IOException {
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
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				commands.get(0).getResult());
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
		assertEquals("[]"
	}

	@Test
	public void testBatchRefUpdateOneRefWrongOldValueNonAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(B
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(false);
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.OK
		assertEquals("[HEAD
				.keySet().toString());
		assertEquals(A.getId()
		assertEquals(B.getId()
	}

	@Test
	public void testBatchRefUpdateOneRefWrongOldValueAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(B
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				commands.get(0).getResult());
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
		assertEquals("[HEAD
		assertEquals(A.getId()
	}

	@Test
	public void testBatchRefDeleteNonExistentRefNonAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(A
						ReceiveCommand.Type.DELETE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(false);
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				commands.get(1).getResult());
		assertEquals("[HEAD
		assertEquals(B.getId()
	}

	@Test
	public void testBatchRefDeleteNonExistentRefAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(A
						ReceiveCommand.Type.DELETE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(0)));
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				commands.get(1).getResult());
		assertEquals("[HEAD
		assertEquals(A.getId()
	}

