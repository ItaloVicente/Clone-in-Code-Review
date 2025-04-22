	public void testFetchBySHA1() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(B.name())));
			assertTrue(dst.getObjectDatabase().has(A_txt));
		}
	}
