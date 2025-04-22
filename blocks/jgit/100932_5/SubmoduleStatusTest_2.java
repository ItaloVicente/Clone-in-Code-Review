	public void repositoryWithInitializedSubmodule() throws Exception {
		String path = "sub";
		Repository subRepo = Git.init().setBare(false)
				.setDirectory(new File(db.getWorkTree()
				.getRepository();
		assertNotNull(subRepo);

		TestRepository<?> subTr = new TestRepository<>(subRepo);
		ObjectId id = subTr.branch(Constants.HEAD).commit().create().copy();

