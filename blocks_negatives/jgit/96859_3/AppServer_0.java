		protected UserIdentity loadUser(String who) {
			return null;
		}

		@Override
		protected void loadUsers() throws IOException {
			putUser(username, new Password(password), new String[] { role });
