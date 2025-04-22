	public void testPersistable() {
		CommitEditorInput input = new CommitEditorInput(new RepositoryCommit(
				repository, commit));
		XMLMemento memento = XMLMemento.createWriteRoot("test");
		input.getPersistable().saveState(memento);
		CommitEditorInputFactory factory = new CommitEditorInputFactory();
		IAdaptable created = factory.createElement(memento);
		assertNotNull(created);
		assertTrue(created instanceof CommitEditorInput);
		CommitEditorInput createdInput = (CommitEditorInput) created;
		assertEquals(input.getCommit().getRevCommit().name(), createdInput
				.getCommit().getRevCommit().name());
		assertEquals(input.getCommit().getRepository().getDirectory(),
				createdInput.getCommit().getRepository().getDirectory());
