	public static final ConnectorDescriptor DESCRIPTOR = new ConnectorDescriptor() {

		@Override
		public String getIdentityAgent() {
			return ENV_SSH_AUTH_SOCK;
		}

		@Override
		public String getDisplayName() {
			return Texts.get().unixDefaultAgent;
		}
	};

