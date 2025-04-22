	private static class FakeUserAuthGSS extends UserAuthGSS {
		@Override
		protected Boolean doAuth(Buffer buffer
				throws Exception {
			if (initial) {
				ServerSession session = getServerSession();
				Buffer b = session.createBuffer(
						SshConstants.SSH_MSG_USERAUTH_INFO_REQUEST);
				b.putBytes(KRB5_MECH.getDER());
				session.writePacket(b);
				return null;
			}
			return Boolean.FALSE;
		}
	}

	private List<NamedFactory<UserAuth>> getAuthFactories() {
		List<NamedFactory<UserAuth>> authentications = new ArrayList<>();
		authentications.add(
				ServerAuthenticationManager.DEFAULT_USER_AUTH_PUBLIC_KEY_FACTORY);
		authentications.add(new UserAuthGSSFactory() {
			@Override
			public UserAuth create() {
				return new FakeUserAuthGSS();
			}
		});
		return authentications;
	}

