	@Test
	public void putMatchingWithNoMatchingRefs() throws Exception {
		PushCertificate addMaster = newCert(
				command(zeroId()
				command(zeroId()
		store.put(addMaster
		assertEquals(NO_CHANGE
	}

	@Test
	public void putMatchingWithSomeMatchingRefs() throws Exception {
		PushCertificate addMasterAndBranch = newCert(
				command(zeroId()
				command(zeroId()
		store.put(addMasterAndBranch
				Collections.singleton(addMasterAndBranch.getCommands().get(0)));
		assertEquals(NEW
		assertCerts("refs/heads/master"
		assertCerts("refs/heads/branch");
	}

