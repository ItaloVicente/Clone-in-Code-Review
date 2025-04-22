		try {
			getContent(id, f);
			fail("expected RuntimeException");
		} catch (RuntimeException e) {
			String error = String.format(
					"Invalid pathInfo '/%s' does not match '/{SHA-256}'", id);
			assertEquals(formatErrorMessage(SC_UNPROCESSABLE_ENTITY, error),
					e.getMessage());
		}
