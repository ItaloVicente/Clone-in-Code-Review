		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ptr.encode(baos);
			assertEquals(
							+ s + "\nsize 4\n"
					baos.toString(UTF_8.name()));
		}
