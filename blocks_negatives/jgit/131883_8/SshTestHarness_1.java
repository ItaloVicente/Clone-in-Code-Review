	@Test
	public void testSshCloneWithGlobalIdentity() throws Exception {
		cloneWith(
						+ "/doesntmatter",
				null,
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshCloneWithDefaultIdentity() throws Exception {
		File idRsa = new File(privateKey1.getParentFile(), "id_rsa");
		Files.copy(privateKey1.toPath(), idRsa.toPath());
				+ "/doesntmatter", null);
	}

	@Test
	public void testSshCloneWithConfig() throws Exception {
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshCloneWithConfigEncryptedUnusedKey() throws Exception {
		File encryptedKey = new File(sshDir, "id_dsa");
		try (InputStream in = SshTestBase.class
				.getResourceAsStream("id_dsa_test")) {
			Files.copy(in, encryptedKey.toPath());
