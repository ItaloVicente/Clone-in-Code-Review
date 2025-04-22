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
	public void testCheckEmptyCiphersAndMacs() throws Exception {
		final GitSSHService sshService = new GitSSHService();
		final File certDir = createTempDirectory();

		String idleTimeout = "10000";
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
	public void testCheckNullCiphersAndMacs() throws Exception {
		final GitSSHService sshService = new GitSSHService();
		final File certDir = createTempDirectory();

		String idleTimeout = "10000";
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
	public void testWithWrongCiphersAndMacs() throws Exception {
		final GitSSHService sshService = new GitSSHService();
		final File certDir = createTempDirectory();

		String idleTimeout = "10000";
		String ciphers = "aes126-cbc
		sshService.setup(certDir
				mock(UploadPackFactory.class)
				executorService

		sshService.start();
		assertTrue(sshService.isRunning());

		List<String> ciphersReaded = sshService.getSshServer().getCipherFactoriesNames();
		List<String> macsReaded = sshService.getSshServer().getMacFactoriesNames();

		assertThat(ciphersReaded).hasSize(5);
		checkCiphersName(ciphersReaded);

		assertThat(macsReaded).hasSize(6);
		checkMacsName(macsReaded);

		assertThat(sshService.getSshServer().getProperties().get(SshServer.IDLE_TIMEOUT)).isEqualTo(idleTimeout);

		sshService.stop();

		assertFalse(sshService.isRunning());
	}

	private void checkCiphersName(List<String> ciphersReaded) {
		for (String cipher : ciphersReaded) {
			assertThat(BuiltinCiphers.fromFactoryName(cipher)).isNotNull();
		}
	}

	private void checkMacsName(List<String> macsReaded) {
		for (String mac : macsReaded) {
			assertThat(BuiltinMacs.fromFactoryName(mac)).isNotNull();
		}
	}
