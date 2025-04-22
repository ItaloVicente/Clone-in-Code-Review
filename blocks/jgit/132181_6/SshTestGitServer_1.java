	protected void configureAuthentication() {
		server.setUserAuthFactories(getAuthFactories());
		server.setPasswordAuthenticator(null);
		server.setKeyboardInteractiveAuthenticator(null);
		server.setHostBasedAuthenticator(null);
		server.setGSSAuthenticator(new GSSAuthenticator() {
			@Override
			public boolean validateInitialUser(ServerSession session
					String user) {
				return false;
			}
		});
		server.setPublickeyAuthenticator((userName
			return SshTestGitServer.this.testUser.equals(userName) && KeyUtils
					.compareKeys(SshTestGitServer.this.testKey
		});
	}

	@NonNull
	protected List<NamedFactory<Command>> configureSubsystems() {
		server.setFileSystemFactory(new VirtualFileSystemFactory() {

			@Override
			protected Path computeRootDir(Session session) throws IOException {
				return SshTestGitServer.this.repository.getDirectory()
						.getParentFile().getAbsoluteFile().toPath();
			}
		});
		return Collections
				.singletonList((new SftpSubsystemFactory.Builder()).build());
	}

	protected void configureShell() {
		server.setShellFactory(null);
	}

