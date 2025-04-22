			@Override
			protected Path computeRootDir(Session session) throws IOException {
				return SshTestGitServer.this.repository.getDirectory()
						.getParentFile().getAbsoluteFile().toPath();
			}
		});
		server.setUserAuthFactories(getAuthFactories());
		server.setSubsystemFactories(Collections
				.singletonList((new SftpSubsystemFactory.Builder()).build()));
		server.setShellFactory(null);
		server.setPasswordAuthenticator(null);
		server.setKeyboardInteractiveAuthenticator(null);
		server.setHostBasedAuthenticator(null);
		server.setGSSAuthenticator(new GSSAuthenticator() {
			@Override
			public boolean validateInitialUser(ServerSession session,
					String user) {
				return false;
			}
		});
		server.setPublickeyAuthenticator((userName, publicKey, session) -> {
			return SshTestGitServer.this.testUser.equals(userName) && KeyUtils
					.compareKeys(SshTestGitServer.this.testKey, publicKey);
		});
