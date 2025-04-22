	@Test
	public void addSubmoduleWithInvalidPath() throws Exception {
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setPath("-invalid-path");
		try {
			command.call().close();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage()
					"Invalid submodule path '-invalid-path'");
		}
	}

	@Test
	public void addSubmoduleWithInvalidUri() throws Exception {
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setPath("valid-path");
		command.setURI("-upstream");
		try {
			command.call().close();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage()
		}
	}

