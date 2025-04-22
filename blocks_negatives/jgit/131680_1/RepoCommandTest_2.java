		try (BufferedReader reader = Files.newBufferedReader(hello.toPath(),
				UTF_8)) {
			String content = reader.readLine();
			assertEquals("The destination file should have expected content",
					"master world", content);
		}
