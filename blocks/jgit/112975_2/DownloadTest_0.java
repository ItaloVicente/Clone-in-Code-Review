		String error = String.format(
				"Invalid pathInfo '/%s' does not match '/{SHA-256}'"
		exception.expect(RuntimeException.class);
		exception.expectMessage(
				formatErrorMessage(SC_UNPROCESSABLE_ENTITY
		getContent(id
