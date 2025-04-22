	@Test
	void testPullEmptyRepository() throws Exception {
		assertThrows(NoHeadException.class
			Repository empty = createWorkRepository();
			RefUpdate delete = empty.updateRef(Constants.HEAD
			delete.setForceUpdate(true);
			delete.delete();
			Git.wrap(empty).pull().call();
		});
