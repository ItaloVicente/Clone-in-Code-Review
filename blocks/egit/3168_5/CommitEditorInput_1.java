	public RepositoryCommit getCommit() {
		return this.commit;
	}

	public void saveState(IMemento memento) {
		CommitEditorInputFactory.saveState(memento, this);
	}

	public String getFactoryId() {
		return CommitEditorInputFactory.ID;
	}

