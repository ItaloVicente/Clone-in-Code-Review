				.getRepository()) {
			File hello = new File(localDb.getWorkTree()
			assertTrue("The Hello file should exist"
			File foohello = new File(localDb.getWorkTree()
			assertFalse("The foo/Hello file should be skipped"
					foohello.exists());
			try (BufferedReader reader = new BufferedReader(
					new FileReader(hello))) {
				String content = reader.readLine();
				assertEquals("The Hello file should have expected content"
						"branch world"
			}
		}
