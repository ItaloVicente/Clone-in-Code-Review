	public AddNoteCommand notesAdd() {
		return new AddNoteCommand(repo);
	}

	public RemoveNoteCommand notesRemove() {
		return new RemoveNoteCommand(repo);
	}

	public ListNotesCommand notesList() {
		return new ListNotesCommand(repo);
	}

	public ShowNoteCommand notesShow() {
		return new ShowNoteCommand(repo);
	}

