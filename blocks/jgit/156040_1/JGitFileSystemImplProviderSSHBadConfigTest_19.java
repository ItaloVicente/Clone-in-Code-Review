	@Override
	public Map<String
		Map<String

		gitPrefs.put(GIT_SSH_ENABLED
		gitPrefs.put(GIT_SSH_IDLE_TIMEOUT

		return gitPrefs;
	}

	@Test
	public void testCheckDefaultSSHIdleWithInvalidArg() throws IOException {
		assertEquals(JGitFileSystemProviderConfiguration.DEFAULT_SSH_IDLE_TIMEOUT
				provider.getGitSSHService().getProperties().get(SshServer.IDLE_TIMEOUT));
	}
