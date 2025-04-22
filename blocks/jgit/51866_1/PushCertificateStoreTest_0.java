	@Test
	public void saveInBatch() throws Exception {
		BatchRefUpdate batch = repo.getRefDatabase().newBatchUpdate();
		PushCertificate addMaster = newCert(
				command(zeroId()
		store.put(addMaster
		store.save(batch);

		List<ReceiveCommand> commands = batch.getCommands();
		assertEquals(1
		ReceiveCommand cmd = commands.get(0);

		try (RevWalk rw = new RevWalk(repo)) {
			assertEquals("refs/meta/push-certs"
			assertEquals(ReceiveCommand.Result.NOT_ATTEMPTED
			batch.execute(rw
			assertEquals(ReceiveCommand.Result.OK
		}
	}

