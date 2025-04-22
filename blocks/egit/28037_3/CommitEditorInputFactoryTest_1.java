	@Test
	public void shouldPersistAndRestoreStashCommit() {
		RepositoryCommit stashCommit = new RepositoryCommit(
				repository, commit);
		stashCommit.setStash(true);

		new CommitEditorInput(stashCommit).saveState(memento);
		IAdaptable restored = new CommitEditorInputFactory()
				.createElement(memento);

		assertNotNull(restored);
		assertThat(restored, instanceOf(CommitEditorInput.class));
		assertThat(stashCommit,
				isSameCommit(((CommitEditorInput) restored).getCommit()));
	}
