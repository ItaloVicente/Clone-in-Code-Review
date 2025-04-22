	@Test
	public void addSubmoduleWithInvalidName() throws Exception {
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setName("-invalid-name");
		command.setPath("valid-path");
		try {
			command.call().close();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid submodule name '-invalid-name'"
					e.getMessage());
		}
	}

	@Test
	public void addSubmoduleWithInvalidPath() throws Exception {
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setName("valid-name");
		command.setPath("-invalid-path");
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
		command.setName("valid-name");
		command.setPath("valid-path");
		command.setURI("-upstream");
		try {
			command.call().close();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid submodule URL '-upstream'"
		}
	}

	@Test
	public void denySubmoduleWithDotDot() throws Exception {
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setName("dir/../");
		command.setPath("sub");
		command.setURI(db.getDirectory().toURI().toString());
		try {
			command.call();
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

