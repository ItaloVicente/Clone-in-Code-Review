
	@Test
	public void testWindowsReservedNames() {
		SystemReader original = SystemReader.getInstance();
		try {
			SystemReader.setInstance(new MockSystemReader() {
				public boolean isWindows() {
					return true;
				}
			});
			assertValid(false
			assertValid(false
			assertValid(false
			assertValid(true
			assertValid(true
		} finally {
			SystemReader.setInstance(original);
		}
	}
