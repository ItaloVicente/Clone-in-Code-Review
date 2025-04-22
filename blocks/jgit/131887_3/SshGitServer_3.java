		server.setGSSAuthenticator(new GSSAuthenticator() {
			@Override
			public boolean validateInitialUser(ServerSession session
					String user) {
				return false;
			}
		});
