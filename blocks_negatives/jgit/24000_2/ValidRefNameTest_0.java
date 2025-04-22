		SystemReader original = SystemReader.getInstance();
		try {
			SystemReader.setInstance(new MockSystemReader() {
				public boolean isWindows() {
					return true;
				}
			});
			assertValid(false, "refs/heads/con");
			assertValid(false, "refs/con/x");
			assertValid(false, "con/heads/x");
			assertValid(true, "refs/heads/conx");
			assertValid(true, "refs/heads/xcon");
		} finally {
			SystemReader.setInstance(original);
		}
