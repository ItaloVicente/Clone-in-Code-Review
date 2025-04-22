	@Test
	public void putMatchingWithNoMatchingRefsInBatchOnEmptyRef()
			throws Exception {
		PushCertificate addMaster = newCert(
				command(zeroId()
				command(zeroId()
		store.put(addMaster
		BatchRefUpdate batch = repo.getRefDatabase().newBatchUpdate();
		assertFalse(store.save(batch));
		assertEquals(0
	}

	@Test
	public void putMatchingWithNoMatchingRefsInBatchOnNonEmptyRef()
			throws Exception {
		PushCertificate addMaster = newCert(
				command(zeroId()
		store.put(addMaster
		assertEquals(NEW

		PushCertificate addBranch = newCert(
				command(zeroId()
		store.put(addBranch
		BatchRefUpdate batch = repo.getRefDatabase().newBatchUpdate();
		assertFalse(store.save(batch));
		assertEquals(0
	}

