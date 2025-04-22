	@Test
	public void testFetchBySHA1() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(B.name())));
		}

		assertTrue(dst.hasObject(A_txt));
	}

	@Test
	public void testFetchBySHA1Unreachable() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		try (Transport t = Transport.open(dst
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + unreachableCommit.name() + " not valid"));
			t.fetch(NullProgressMonitor.INSTANCE
					.singletonList(new RefSpec(unreachableCommit.name())));
		}
	}

	@Test
	public void testFetchBySHA1UnreachableByAdvertiseRefsHook()
			throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		advertiseRefsHook = new AbstractAdvertiseRefsHook() {
			@Override
			protected Map<String
					RevWalk revWalk) {
				return Collections.emptyMap();
			}
		};

		try (Transport t = Transport.open(dst
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + A.name() + " not valid"));
			t.fetch(NullProgressMonitor.INSTANCE
					.singletonList(new RefSpec(A.name())));
		}
	}

