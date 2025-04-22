
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
