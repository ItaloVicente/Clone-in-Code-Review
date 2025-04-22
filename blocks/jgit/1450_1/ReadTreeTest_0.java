	public void testDontOverwriteDirtyFile() throws IOException {
		setupCase(mk("foo")
		writeTrashFile("foo"
		try {
			checkout();
			fail("Didn't got the expected conflict");
		} catch (CheckoutConflictException e) {
			assertIndex(mk("foo"));
			assertWorkDir(mkmap("foo"
			assertTrue(getConflicts().equals(Arrays.asList("foo")));
			assertTrue(new File(trash
		}
	}

