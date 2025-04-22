	@Test
	public void testFindRemoteRefUpdatesWithLeases() throws IOException {
		final RefSpec specA = new RefSpec("+refs/heads/a:refs/heads/b");
		final RefSpec specC = new RefSpec("+refs/heads/c:refs/heads/d");
		final Collection<RefSpec> specs = Arrays.asList(specA
		final Map<String
		leases.put("refs/heads/b"
				new RefLeaseSpec("refs/heads/b"

		Collection<RemoteRefUpdate> result;
		try (Transport transport = Transport.open(db
			result = transport.findRemoteRefUpdatesFor(specs
		}

		assertEquals(2
		boolean foundA = false;
		boolean foundC = false;
		for (final RemoteRefUpdate rru : result) {
			if ("refs/heads/a".equals(rru.getSrcRef())
					&& "refs/heads/b".equals(rru.getRemoteName())) {
				foundA = true;
				assertEquals(db.exactRef("refs/heads/c").getObjectId()
						rru.getExpectedOldObjectId());
			}
			if ("refs/heads/c".equals(rru.getSrcRef())
					&& "refs/heads/d".equals(rru.getRemoteName())) {
				foundC = true;
				assertNull(rru.getExpectedOldObjectId());
			}
		}
		assertTrue(foundA);
		assertTrue(foundC);
	}

