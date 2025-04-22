		try {
			getContent(id, f);
			fail("expected RuntimeException");
		} catch (RuntimeException e) {
			String error = String.format("Object '%s' not found", id.getName());
			assertEquals(formatErrorMessage(SC_NOT_FOUND, error),
					e.getMessage());
		}
