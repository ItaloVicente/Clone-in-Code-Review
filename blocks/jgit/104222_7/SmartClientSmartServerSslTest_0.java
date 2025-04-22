	private CredentialsProvider testCredentials = new CredentialsProvider() {

		@Override
		public boolean isInteractive() {
			return false;
		}

		@Override
		public boolean supports(CredentialItem... items) {
			for (CredentialItem item : items) {
				if (item instanceof CredentialItem.InformationalMessage) {
					continue;
				}
				if (item instanceof CredentialItem.YesNoType) {
					continue;
				}
				return false;
			}
			return true;
		}

		@Override
		public boolean get(URIish uri
				throws UnsupportedCredentialItem {
			for (CredentialItem item : items) {
				if (item instanceof CredentialItem.InformationalMessage) {
					continue;
				}
				if (item instanceof CredentialItem.YesNoType) {
					((CredentialItem.YesNoType) item).setValue(true);
					continue;
				}
				return false;
			}
			return true;
		}
	};

