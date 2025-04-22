	private int gitDaemonPort;

	@Override
	public Map<String
		Map<String
		gitPrefs.put(GIT_DAEMON_ENABLED
		gitDaemonPort = findFreePort();
		gitPrefs.put(GIT_DAEMON_PORT
		return gitPrefs;
	}

	@Test
	public void proxyTest() throws IOException {

		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo

		assertTrue(origin instanceof JGitFileSystemProxy);
		JGitFileSystemProxy proxy = (JGitFileSystemProxy) origin;
		JGitFileSystem realJGitFileSystem = proxy.getRealJGitFileSystem();
		assertTrue(realJGitFileSystem instanceof JGitFileSystemImpl);

		assertTrue(proxy.equals(realJGitFileSystem));
		assertTrue(realJGitFileSystem.equals(proxy));
	}
