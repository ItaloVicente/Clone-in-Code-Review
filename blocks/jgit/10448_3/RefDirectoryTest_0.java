	@Test
	public void testBatchRefUpdateSimpleNoForce() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		List<ReceiveCommand> commands = Arrays.asList(
				newCommand(A
						ReceiveCommand.Type.UPDATE)
				newCommand(B
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.addCommand(commands);
		batchUpdate
				.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.REJECTED_NONFASTFORWARD
				.get(1).getResult());
		assertEquals("[HEAD
				.keySet().toString());
		assertEquals(B.getId()
		assertEquals(B.getId()
	}

	@Test
	public void testBatchRefUpdateSimpleForce() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		List<ReceiveCommand> commands = Arrays.asList(
				newCommand(A
						ReceiveCommand.Type.UPDATE)
				newCommand(B
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate
				.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.OK
		assertEquals("[HEAD
				.keySet().toString());
		assertEquals(B.getId()
		assertEquals(A.getId()
	}

	@Test
	public void testBatchRefUpdateConflict() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		List<ReceiveCommand> commands = Arrays.asList(
				newCommand(A
						ReceiveCommand.Type.UPDATE)
				newCommand(null
						ReceiveCommand.Type.CREATE)
				newCommand(null
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate
				.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				.getResult());
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				.getResult());
		assertEquals("[HEAD
				.keySet().toString());
		assertEquals(B.getId()
		assertEquals(B.getId()
	}

	@Test
	public void testBatchRefUpdateConflictThanksToDelete() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		List<ReceiveCommand> commands = Arrays.asList(
				newCommand(A
						ReceiveCommand.Type.UPDATE)
				newCommand(null
						ReceiveCommand.Type.CREATE)
				newCommand(B
						ReceiveCommand.Type.DELETE));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate
				.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.OK
				.getResult());
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.OK
		assertEquals("[HEAD
				.keySet().toString());
		assertEquals(A.getId()
	}


	private static ReceiveCommand newCommand(RevCommit a
			String string
		ReceiveCommand ret = new ReceiveCommand(a != null ? a.getId() : null
				b != null ? b.getId() : null
		ret.setResult(ReceiveCommand.Result.NOT_ATTEMPTED);
		return ret;
	}

