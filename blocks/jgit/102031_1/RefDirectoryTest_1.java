	@Test
	public void testPackedRefsLockFailure() throws Exception {
		writeLooseRef("refs/heads/master"
		refdir.setRetrySleepMs(Arrays.asList(0
		LockFile myLock = refdir.lockPackedRefs();
		try {
			refdir.pack(Arrays.asList("refs/heads/master"));
			fail("expected LockFailedException");
		} catch (LockFailedException e) {
			assertEquals(refdir.packedRefsFile.getPath()
		} finally {
			myLock.unlock();
		}
		Ref ref = refdir.getRef("refs/heads/master");
		assertEquals(Storage.LOOSE
	}

