	@Test
	public void saveMatchingWithNoMatchingRefs() throws Exception {
		BatchRefUpdate batch = repo.getRefDatabase().newBatchUpdate();
		PushCertificate addMaster = newCert(
				command(zeroId()
				command(zeroId()
		store.put(addMaster
		store.saveMatching(batch);
		assertEquals(0
	}

	@Test
	public void saveMatchingWithSomeMatchingRefs() throws Exception {
		BatchRefUpdate batch = repo.getRefDatabase().newBatchUpdate();
		batch.addCommand(new ReceiveCommand(zeroId()
		PushCertificate addMasterAndBranch = newCert(
				command(zeroId()
				command(zeroId()
		store.put(addMasterAndBranch
		store.saveMatching(batch);

		List<ReceiveCommand> commands = batch.getCommands();
		assertEquals(2
		ReceiveCommand cmd = commands.get(1);
		assertEquals("refs/meta/push-certs"
		assertEquals(ReceiveCommand.Result.NOT_ATTEMPTED

		try (RevWalk rw = new RevWalk(repo)) {
			batch.execute(rw
			assertEquals(ReceiveCommand.Result.OK
			store.clear();
			store.close();
		}

		assertCerts("refs/heads/master"
		assertCerts("refs/heads/branch");
	}

