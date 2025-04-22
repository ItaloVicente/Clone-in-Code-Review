	private static void assertContents(Path path
			throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path
			String content = reader.readLine();
			assertEquals("Unexpected content in " + path.getFileName()
					expected
		}
	}

