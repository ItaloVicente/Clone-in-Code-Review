	@Test
	public void testFetchBySHA1Unreachable() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + unreachableCommit.name() + " not valid"));
			t.fetch(NullProgressMonitor.INSTANCE
					.singletonList(new RefSpec(unreachableCommit.name())));
