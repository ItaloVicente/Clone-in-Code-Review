		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
		}

		tempFiles.add(temp);

		return temp;
	}

	@AfterClass
	@BeforeClass
	public static void cleanup() {
		for (final File tempFile : tempFiles) {
			try {
				FileUtils.delete(tempFile
			} catch (IOException ignore) {
			}
		}
	}

	@Test
	public void testStartStop() throws Exception {
		final GitSSHService sshService = new GitSSHService();
		final File certDir = createTempDirectory();

		sshService.setup(certDir
				mock(JGitFileSystemProvider.RepositoryResolverImpl.class)

		sshService.start();
		assertTrue(sshService.isRunning());

		sshService.stop();

		assertFalse(sshService.isRunning());
	}

	@Test
	public void testStartStopAlgo2() throws Exception {
		final GitSSHService sshService = new GitSSHService();
		final File certDir = createTempDirectory();

		sshService.setup(certDir
				mock(JGitFileSystemProvider.RepositoryResolverImpl.class)

		sshService.start();
		assertTrue(sshService.isRunning());

		sshService.stop();

		assertFalse(sshService.isRunning());
	}

	@Test
	public void testCheckTimeout() throws Exception {
		final GitSSHService sshService = new GitSSHService();
		final File certDir = createTempDirectory();

		String idleTimeout = "10000";
		sshService.setup(certDir
				mock(UploadPackFactory.class)
				executorService);

		sshService.start();
		assertTrue(sshService.isRunning());

		assertThat(sshService.getSshServer().getProperties().get(SshServer.IDLE_TIMEOUT)).isEqualTo(idleTimeout);

		sshService.stop();

		assertFalse(sshService.isRunning());
	}

	@Test
	public void testCheckAlgo() throws Exception {
		final GitSSHService sshService = new GitSSHService();
		final File certDir = createTempDirectory();

		try {
			sshService.setup(certDir
					mock(UploadPackFactory.class)
					executorService);
			fail("has to fail");
		} catch (final Exception ex) {
			assertThat(ex.getMessage()).contains("'xxxx'");
		}
	}

	@Test
	public void testCheckSetupParameters() throws Exception {
		final GitSSHService sshService = new GitSSHService();
		final File certDir = createTempDirectory();

		try {
			sshService.setup(null
					mock(JGitFileSystemProvider.RepositoryResolverImpl.class)
			fail("has to fail");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage()).contains("'certDir'");
		}

		try {
			sshService.setup(certDir
					mock(JGitFileSystemProvider.RepositoryResolverImpl.class)
			fail("has to fail");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage()).contains("'sshIdleTimeout'");
		}

		try {
			sshService.setup(certDir
					mock(JGitFileSystemProvider.RepositoryResolverImpl.class)
			fail("has to fail");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage()).contains("'sshIdleTimeout'");
		}

		try {
			sshService.setup(certDir
					mock(JGitFileSystemProvider.RepositoryResolverImpl.class)
			fail("has to fail");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage()).contains("'algorithm'");
		}

		try {
			sshService.setup(certDir
					mock(JGitFileSystemProvider.RepositoryResolverImpl.class)
			fail("has to fail");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage()).contains("'algorithm'");
		}

		try {
			sshService.setup(certDir
					mock(JGitFileSystemProvider.RepositoryResolverImpl.class)
			fail("has to fail");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage()).contains("'receivePackFactory'");
		}

		try {
			sshService.setup(certDir
					null
			fail("has to fail");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage()).contains("'repositoryResolver'");
		}

		try {
			sshService.setup(certDir
					mock(UploadPackFactory.class)
					executorService);
		} catch (IllegalArgumentException ex) {
			fail("should not fail");
		}
	}

	@Test
	public void testCheckCiphersAndMacs() throws Exception {
		final GitSSHService sshService = new GitSSHService();
		final File certDir = createTempDirectory();

		String idleTimeout = "10000";
		String ciphers = "aes128-cbc
		String macs = "hmac-md5
		sshService.setup(certDir
				mock(UploadPackFactory.class)
				executorService

		sshService.start();
		assertTrue(sshService.isRunning());

		List<String> ciphersReaded = sshService.getSshServer().getCipherFactoriesNames();
		List<String> macsReaded = sshService.getSshServer().getMacFactoriesNames();

		assertThat(ciphersReaded).hasSize(7);
		checkCiphersName(ciphersReaded);

		assertThat(macsReaded).hasSize(6);
		checkMacsName(macsReaded);

		assertThat(sshService.getSshServer().getProperties().get(SshServer.IDLE_TIMEOUT)).isEqualTo(idleTimeout);

		sshService.stop();

		assertFalse(sshService.isRunning());
	}

	@Test
	public void testCheckEmptyCiphers() throws Exception {
		final GitSSHService sshService = new GitSSHService();
		final File certDir = createTempDirectory();

		String idleTimeout = "10000";
		String macs = "hmac-md5
		sshService.setup(certDir
				mock(UploadPackFactory.class)
				executorService
