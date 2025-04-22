	@Test
	public void testHippUserConfig() throws Exception {
		SystemReader testReader = SystemReader.getInstance();
		try {
			SystemReader.setInstance(null);
			FileBasedConfig config = SystemReader.getInstance()
					.openUserConfig(null, FS.DETECTED);
			config.load();
			String[] values = config.getStringList("testsection",
					"testsubsection.testname", "testname");
			if (values == null) {
				System.out.println("No entries found");
			} else {
				System.out.println("Found " + values.length + " entries");
				if (values.length > 0) {
					config.unsetSection("testsection",
							"testsubsection.testname");
					config.unsetSection("testsection", "testsubsection");
					config.unsetSection("testsection", null);
					config.save();
				}
			}
		} finally {
			SystemReader.setInstance(testReader);
		}
	}

