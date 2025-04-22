		try (BufferedReader reader = Files.newBufferedReader(hello.toPath(),
				UTF_8)) {
			String content = reader.readLine();
			assertEquals("submodule content should be as expected",
					"master world", content);
		}
