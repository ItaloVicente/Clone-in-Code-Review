	@Test(expected = NoHeadException.class)
	public void testPullEmptyRepository() throws Exception {
		Repository empty = createWorkRepository();
		RefUpdate delete = empty.updateRef(Constants.HEAD, true);
		delete.setForceUpdate(true);
		delete.delete();
		Git.wrap(empty).pull().call();
