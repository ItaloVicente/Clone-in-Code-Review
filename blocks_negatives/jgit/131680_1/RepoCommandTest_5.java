			try (BufferedReader reader = Files.newBufferedReader(hello.toPath(),
					UTF_8)) {
				String content = reader.readLine();
				assertEquals("The Hello file should have expected content",
						"branch world", content);
			}
