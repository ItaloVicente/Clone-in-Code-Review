	@Test
	void testKeyboardInteractiveAuthCorrectPasswordTooLate() throws Exception {
		assertThrows(TransportException.class
			server.enableKeyboardInteractiveAuthentication();
			TestCredentialsProvider provider = new TestCredentialsProvider(
					"wrongpass"
					TEST_USER.toUpperCase(Locale.ROOT));
					"Host git"
					"HostName localhost"
					"Port " + testPort
					"User " + TEST_USER
					"PreferredAuthentications keyboard-interactive");
		});
	}

	static Collection<Arguments> sshKeys() {
		List<Arguments> result = new ArrayList<>();
		for (String k : KEY_RESOURCES) {
			result.add(Arguments.of(k));
		}
		return result;
