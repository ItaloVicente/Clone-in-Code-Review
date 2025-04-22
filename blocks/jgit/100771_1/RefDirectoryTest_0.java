		assertTrue(batchUpdate.isAtomic());
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertTrue(ReceiveCommand.isTransactionAborted(commands.get(0)));
		assertEquals(ReceiveCommand.Result.REJECTED_NONFASTFORWARD
				.get(1).getResult());
		assertEquals("[HEAD
				.keySet().toString());
		assertEquals(A.getId()
		assertEquals(B.getId()
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
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(B
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAtomic(atomic);
