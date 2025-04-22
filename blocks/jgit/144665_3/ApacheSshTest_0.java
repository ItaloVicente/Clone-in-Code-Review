	@Test
	public void testHashedKnownHosts() throws Exception {
		assertTrue("Failed to delete known_hosts"
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"HashKnownHosts yes"
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
		List<LogEntry> messages = provider.getLog();
		assertFalse("Expected user interaction"
		assertEquals(
				"Expected to be asked about the key
				messages.size());
		assertTrue("~/.ssh/known_hosts should exist now"
		File clonedAgain = new File(getTemporaryDirectory()
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
		List<String> lines = Files.readAllLines(knownHosts.toPath()).stream()
				.filter(s -> s != null && s.length() >= 1 && s.charAt(0) != '#'
						&& !s.trim().isEmpty())
				.collect(Collectors.toList());
		assertEquals("Unexpected number of known_hosts lines"
		String line = lines.get(0);
		assertFalse("Found host in line"
		assertFalse("Found IP in line"
		assertTrue("Hash not found"
		KnownHostEntry entry = KnownHostEntry.parseKnownHostEntry(line);
		assertTrue("Hash doesn't match localhost"
				entry.isHostMatch("localhost"
						|| entry.isHostMatch("127.0.0.1"
	}

