	public void shouldPersistAndRestoreCommit() {
		RepositoryCommit repositoryCommit = new RepositoryCommit(
				repository, commit);

		new CommitEditorInput(repositoryCommit).saveState(memento);
		IAdaptable restored = new CommitEditorInputFactory()
				.createElement(memento);

		assertNotNull(restored);
		assertThat(restored, instanceOf(CommitEditorInput.class));
		assertThat(repositoryCommit,
				isSameCommit(((CommitEditorInput) restored).getCommit()));
