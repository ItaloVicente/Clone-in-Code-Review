	@Test
	public void addSubmoduleWithInvalidPath() throws Exception {
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setPath("-invalid-path");
		command.setName("sub");
		try {
			command.call().close();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid submodule path '-invalid-path'"
					e.getMessage());
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
			assertEquals("Invalid submodule URL '-upstream'"
		}
	}

