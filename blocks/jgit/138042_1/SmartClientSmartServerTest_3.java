	@Test
	public void testFetchBySHA1UnreachableByAdvertiseRefsHook()
			throws Exception {
		advertiseRefsHook = new AbstractAdvertiseRefsHook() {
			@Override
			protected Map<String
					RevWalk revWalk) {
				return Collections.emptyMap();
			}
		};

		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + A.name() + " not valid"));
			t.fetch(NullProgressMonitor.INSTANCE
					.singletonList(new RefSpec(A.name())));
		}
	}

	@Test
	public void testInitialClone_Small() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.fetch(NullProgressMonitor.INSTANCE
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B
			fsck(dst
		}
