	private void storeNonNote() {
		ObjectId id = getEntryObjectId();
		FileMode fileMode = getEntryFileMode();

		byte[] name = new byte[getNameLength()];
		getName(name

		NonNoteEntry ent = new NonNoteEntry(name
		if (firstNonNote == null)
			firstNonNote = ent;
		if (lastNonNote != null)
			lastNonNote.next = ent;
		lastNonNote = ent;
	}

