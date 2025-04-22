	public AddNotesCommand notesAdd() {
		return new AddNotesCommand(repo);
	}

	public RemoveNotesCommand notesRemove() {
		return new RemoveNotesCommand(repo);
	}

	public ListNotesCommand notesList() {
		return new ListNotesCommand(repo);
	}

	public ShowNotesCommand notesShow() {
		return new ShowNotesCommand(repo);
	}

