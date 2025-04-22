		SystemReader instance = SystemReader.getInstance();
		try {
			setUnixSystemReader();
			assertEquals("\"" + name + "\""
					Repository.isValidRefName(name));
			setWindowsSystemReader();
			assertEquals("\"" + name + "\""
					Repository.isValidRefName(name));
		} finally {
			SystemReader.setInstance(instance);
		}
	}

	private static void setWindowsSystemReader() {
		SystemReader.setInstance(new MockSystemReader() {
			{
				setWindows();
			}
		});
	}

	private static void setUnixSystemReader() {
		SystemReader.setInstance(new MockSystemReader() {
			{
				setUnix();
			}
		});
	}

	private static void assertInvalidOnWindows(final String name) {
		SystemReader instance = SystemReader.getInstance();
		try {
			setUnixSystemReader();
			assertEquals("\"" + name + "\""
					Repository.isValidRefName(name));
			setWindowsSystemReader();
			assertEquals("\"" + name + "\""
					Repository.isValidRefName(name));
		} finally {
			SystemReader.setInstance(instance);
		}
