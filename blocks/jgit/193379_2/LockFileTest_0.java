
	@Test
	public void testUnlockNoop() throws Exception {
		File f = writeTrashFile("somefile"
		try {
			LockFile lock = new LockFile(f);
			lock.unlock();
			lock.unlock();
		} catch (Throwable e) {
			fail("unlock should be noop if not locked at all.");
		}
	}
