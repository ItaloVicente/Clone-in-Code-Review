	private int attempts = 1;

	protected static class State {

		private int count = 0;

		private char[] password;

		public int getCount() {
			return count;
		}

		public int incCount() {
			return ++count;
		}

		public void setPassword(char[] password) {
			if (this.password != null) {
				Arrays.fill(this.password
			}
			if (password != null) {
				this.password = password.clone();
			} else {
				this.password = null;
			}
		}

		public char[] getPassword() {
			return password;
		}
	}

	private final Map<String

