		try (BufferedReader reader = new BufferedReader(
				new FileReader(hello))) {
			String content = reader.readLine();
			assertEquals("The destination file should have expected content"
					"master world"
		}
