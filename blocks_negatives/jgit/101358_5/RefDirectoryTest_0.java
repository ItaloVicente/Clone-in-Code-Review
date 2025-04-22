	@Test
	public void testBatchRefUpdateSimpleNoForceNonAtomic() throws IOException {
		writeLooseRef("refs/heads/master", A);
		writeLooseRef("refs/heads/masters", B);
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(B, A, "refs/heads/masters",
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(false);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.OK, commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.REJECTED_NONFASTFORWARD, commands
				.get(1).getResult());
		assertEquals("[HEAD, refs/heads/master, refs/heads/masters]", refs
				.keySet().toString());
		assertEquals(B.getId(), refs.get("refs/heads/master").getObjectId());
		assertEquals(B.getId(), refs.get("refs/heads/masters").getObjectId());
	}

	@Test
	public void testBatchRefUpdateSimpleNoForceAtomic() throws IOException {
		writeLooseRef("refs/heads/master", A);
		writeLooseRef("refs/heads/masters", B);
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(B, A, "refs/heads/masters",
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(0)));
		assertEquals(ReceiveCommand.Result.REJECTED_NONFASTFORWARD, commands
				.get(1).getResult());
		assertEquals("[HEAD, refs/heads/master, refs/heads/masters]", refs
				.keySet().toString());
		assertEquals(A.getId(), refs.get("refs/heads/master").getObjectId());
		assertEquals(B.getId(), refs.get("refs/heads/masters").getObjectId());
	}

	@Test
	public void testBatchRefUpdateSimpleForceNonAtomic() throws IOException {
		testBatchRefUpdateSimpleForce(false);
	}

	@Test
	public void testBatchRefUpdateSimpleForceAtomic() throws IOException {
		testBatchRefUpdateSimpleForce(true);
	}

	private void testBatchRefUpdateSimpleForce(boolean atomic) throws IOException {
		writeLooseRef("refs/heads/master", A);
		writeLooseRef("refs/heads/masters", B);
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(B, A, "refs/heads/masters",
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(atomic);
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.OK, commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.OK, commands.get(1).getResult());
		assertEquals("[HEAD, refs/heads/master, refs/heads/masters]", refs
				.keySet().toString());
		assertEquals(B.getId(), refs.get("refs/heads/master").getObjectId());
		assertEquals(A.getId(), refs.get("refs/heads/masters").getObjectId());
	}

	@Test
	public void testBatchRefUpdateNonFastForwardDoesNotDoExpensiveMergeCheckNonAtomic()
			throws IOException {
		testBatchRefUpdateNonFastForwardDoesNotDoExpensiveMergeCheck(false);
	}

	@Test
	public void testBatchRefUpdateNonFastForwardDoesNotDoExpensiveMergeCheckAtomic()
			throws IOException {
		testBatchRefUpdateNonFastForwardDoesNotDoExpensiveMergeCheck(true);
	}

	private void testBatchRefUpdateNonFastForwardDoesNotDoExpensiveMergeCheck(
			boolean atomic) throws IOException {
		writeLooseRef("refs/heads/master", B);
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(B, A, "refs/heads/master",
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(atomic);
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo) {
			@Override
			public boolean isMergedInto(RevCommit base, RevCommit tip) {
				throw new AssertionError("isMergedInto() should not be called");
			}
		}, new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.OK, commands.get(0).getResult());
		assertEquals(A.getId(), refs.get("refs/heads/master").getObjectId());
	}

	@Test
	public void testBatchRefUpdateFileDirectoryConflictNonAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master", A);
		writeLooseRef("refs/heads/masters", B);
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), A, "refs/heads/master/x",
						ReceiveCommand.Type.CREATE),
				new ReceiveCommand(zeroId(), A, "refs/heads",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(false);
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate
				.execute(new RevWalk(diskRepo), NullProgressMonitor.INSTANCE);
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.OK, commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE, commands.get(1)
				.getResult());
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE, commands.get(2)
				.getResult());
		assertEquals("[HEAD, refs/heads/master, refs/heads/masters]", refs
				.keySet().toString());
		assertEquals(B.getId(), refs.get("refs/heads/master").getObjectId());
		assertEquals(B.getId(), refs.get("refs/heads/masters").getObjectId());
	}

	@Test
	public void testBatchRefUpdateFileDirectoryConflictAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master", A);
		writeLooseRef("refs/heads/masters", B);
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), A, "refs/heads/master/x",
						ReceiveCommand.Type.CREATE),
				new ReceiveCommand(zeroId(), A, "refs/heads",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate
				.execute(new RevWalk(diskRepo), NullProgressMonitor.INSTANCE);
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE,
				commands.get(0).getResult());
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(2)));
		assertEquals("[HEAD, refs/heads/master, refs/heads/masters]", refs
				.keySet().toString());
		assertEquals(A.getId(), refs.get("refs/heads/master").getObjectId());
		assertEquals(B.getId(), refs.get("refs/heads/masters").getObjectId());
	}

	@Test
	public void testBatchRefUpdateConflictThanksToDeleteNonAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master", A);
		writeLooseRef("refs/heads/masters", B);
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), A, "refs/heads/masters/x",
						ReceiveCommand.Type.CREATE),
				new ReceiveCommand(B, zeroId(), "refs/heads/masters",
						ReceiveCommand.Type.DELETE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(false);
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.OK, commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.OK, commands.get(1).getResult());
		assertEquals(ReceiveCommand.Result.OK, commands.get(2).getResult());
		assertEquals("[HEAD, refs/heads/master, refs/heads/masters/x]", refs
				.keySet().toString());
		assertEquals(A.getId(), refs.get("refs/heads/masters/x").getObjectId());
	}

	@Test
	public void testBatchRefUpdateConflictThanksToDeleteAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master", A);
		writeLooseRef("refs/heads/masters", B);
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), A, "refs/heads/masters/x",
						ReceiveCommand.Type.CREATE),
				new ReceiveCommand(B, zeroId(), "refs/heads/masters",
						ReceiveCommand.Type.DELETE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.OK, commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.OK, commands.get(1).getResult());
		assertEquals(ReceiveCommand.Result.OK, commands.get(2).getResult());
		assertEquals("[HEAD, refs/heads/master, refs/heads/masters/x]", refs
				.keySet().toString());
		assertEquals(A.getId(), refs.get("refs/heads/masters/x").getObjectId());
	}

	@Test
	public void testBatchRefUpdateUpdateToMissingObjectNonAtomic() throws IOException {
		writeLooseRef("refs/heads/master", A);
		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, bad, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), B, "refs/heads/foo2",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(false);
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), NullProgressMonitor.INSTANCE);
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.REJECTED_MISSING_OBJECT,
				commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.OK, commands.get(1).getResult());
		assertEquals("[HEAD, refs/heads/foo2, refs/heads/master]", refs.keySet()
				.toString());
		assertEquals(A.getId(), refs.get("refs/heads/master").getObjectId());
		assertEquals(B.getId(), refs.get("refs/heads/foo2").getObjectId());
	}

	@Test
	public void testBatchRefUpdateUpdateToMissingObjectAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master", A);
		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, bad, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), B, "refs/heads/foo2",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), NullProgressMonitor.INSTANCE);
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.REJECTED_MISSING_OBJECT,
				commands.get(0).getResult());
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
		assertEquals("[HEAD, refs/heads/master]", refs.keySet()
				.toString());
		assertEquals(A.getId(), refs.get("refs/heads/master").getObjectId());
	}

	@Test
	public void testBatchRefUpdateAddMissingObjectNonAtomic() throws IOException {
		writeLooseRef("refs/heads/master", A);
		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), bad, "refs/heads/foo2",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(false);
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), NullProgressMonitor.INSTANCE);
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.OK, commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.REJECTED_MISSING_OBJECT,
				commands.get(1).getResult());
		assertEquals("[HEAD, refs/heads/master]", refs.keySet()
				.toString());
		assertEquals(B.getId(), refs.get("refs/heads/master").getObjectId());
	}

	@Test
	public void testBatchRefUpdateAddMissingObjectAtomic() throws IOException {
		writeLooseRef("refs/heads/master", A);
		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), bad, "refs/heads/foo2",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), NullProgressMonitor.INSTANCE);
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(0)));
		assertEquals(ReceiveCommand.Result.REJECTED_MISSING_OBJECT,
				commands.get(1).getResult());
		assertEquals("[HEAD, refs/heads/master]", refs.keySet().toString());
		assertEquals(A.getId(), refs.get("refs/heads/master").getObjectId());
	}

	@Test
	public void testBatchRefUpdateOneNonExistentRefNonAtomic()
			throws IOException {
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/foo1",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), B, "refs/heads/foo2",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(false);
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE,
				commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.OK, commands.get(1).getResult());
		assertEquals("[refs/heads/foo2]", refs.keySet().toString());
		assertEquals(B.getId(), refs.get("refs/heads/foo2").getObjectId());
	}

	@Test
	public void testBatchRefUpdateOneNonExistentRefAtomic()
			throws IOException {
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/foo1",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), B, "refs/heads/foo2",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE,
				commands.get(0).getResult());
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
		assertEquals("[]", refs.keySet().toString());
	}

	@Test
	public void testBatchRefUpdateOneRefWrongOldValueNonAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master", A);
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(B, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), B, "refs/heads/foo2",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(false);
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE,
				commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.OK, commands.get(1).getResult());
		assertEquals("[HEAD, refs/heads/foo2, refs/heads/master]", refs
				.keySet().toString());
		assertEquals(A.getId(), refs.get("refs/heads/master").getObjectId());
		assertEquals(B.getId(), refs.get("refs/heads/foo2").getObjectId());
	}

	@Test
	public void testBatchRefUpdateOneRefWrongOldValueAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master", A);
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(B, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), B, "refs/heads/foo2",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE,
				commands.get(0).getResult());
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
		assertEquals("[HEAD, refs/heads/master]", refs.keySet().toString());
		assertEquals(A.getId(), refs.get("refs/heads/master").getObjectId());
	}

	@Test
	public void testBatchRefDeleteNonExistentRefNonAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master", A);
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(A, zeroId(), "refs/heads/foo2",
						ReceiveCommand.Type.DELETE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(false);
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.OK, commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE,
				commands.get(1).getResult());
		assertEquals("[HEAD, refs/heads/master]", refs.keySet().toString());
		assertEquals(B.getId(), refs.get("refs/heads/master").getObjectId());
	}

	@Test
	public void testBatchRefDeleteNonExistentRefAtomic()
			throws IOException {
		writeLooseRef("refs/heads/master", A);
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(A, zeroId(), "refs/heads/foo2",
						ReceiveCommand.Type.DELETE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		assertTrue(batchUpdate.isAtomic());
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(0)));
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE,
				commands.get(1).getResult());
		assertEquals("[HEAD, refs/heads/master]", refs.keySet().toString());
		assertEquals(A.getId(), refs.get("refs/heads/master").getObjectId());
	}

