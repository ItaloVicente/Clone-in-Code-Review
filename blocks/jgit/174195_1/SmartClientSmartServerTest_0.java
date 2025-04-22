	@Test
	public void testFetch_MaxHavesCutoffAfterAckOnly() throws Exception {
		TestRepository dst = createTestRepository();
		try (Transport t = Transport.open(dst.getRepository()
			t.fetch(NullProgressMonitor.INSTANCE
		}
		assertEquals(B

		TestRepository.BranchBuilder b = dst.branch(master);
		for (int i = 0; i < 992; i++)

		RevCommit Z;
		try (TestRepository<Repository> tr = new TestRepository<>(
				remoteRepository)) {
			b = tr.branch(master);
			Z = b.commit().message("Z").create();
		}

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		Writer writer = new OutputStreamWriter(buffer
		TextProgressMonitor monitor = new TextProgressMonitor(writer);
		try (Transport t = Transport.open(dst.getRepository()
			t.fetch(monitor
		}
		assertEquals(Z

		String progressMessages = new String(buffer.toByteArray()
				StandardCharsets.UTF_8);
		Pattern expected = Pattern
				.compile("Receiving objects:\\s+100% \\(1/1\\)\n");
		if (!expected.matcher(progressMessages).find()) {
			System.out.println(progressMessages);
			fail("Expected only one object to be sent");
		}
	}

