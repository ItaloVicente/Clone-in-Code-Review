	@Test
	public void testDisconnect() throws Exception {
		SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		getProjectItem(projectExplorerTree, PROJ1).select();
		String menuString = util
				.getPluginLocalizedValue("DisconnectAction_label");
		ContextMenuHelper.clickContextMenuSync(projectExplorerTree, "Team",
				menuString);
		ResourcesPlugin.getWorkspace().getRoot()
				.refreshLocal(IResource.DEPTH_INFINITE, null);
		Object mapping = childFolder.getSessionProperty(new QualifiedName(
				GitProjectData.class.getName(), "RepositoryMapping"));
		assertNull("Should have no RepositoryMapping", mapping);
	}

