	@Test
	void shouldRaiseErrorOnEmptyUrl() throws Exception {
		assertThrows(BuildException.class
			task.setUri("");
			task.execute();
		});
