		try {
			getContent(id, f);
			fail("expected RuntimeException");
		} catch (RuntimeException e) {
			String error = String.format("Invalid id: : %s", id);
			assertEquals(formatErrorMessage(SC_UNPROCESSABLE_ENTITY, error),
					e.getMessage());
		}
