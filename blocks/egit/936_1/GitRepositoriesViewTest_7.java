	public void testExpandWorkDir() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		Repository myRepository = lookupRepository(repositoryFile);
		List<String> children = Arrays.asList(myRepository.getWorkDir().list());
		List<String> treeChildren = getWorkdirItem(tree, repositoryFile)
				.expand().getNodes();
		assertTrue(children.containsAll(treeChildren)
				&& treeChildren.containsAll(children));
		getWorkdirItem(tree, repositoryFile).expand().getNode(PROJ1).expand()
				.getNode(FOLDER).expand().getNode(FILE1);
