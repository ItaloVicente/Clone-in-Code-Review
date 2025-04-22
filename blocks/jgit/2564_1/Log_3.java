	private NoteMap getNoteMap(String notesRef) throws IOException {
		Ref notes = db.getRef(notesRef);
		if (notes == null)
			return null;
		RevCommit notesCommit = revWalk.parseCommit(notes.getObjectId());
		return NoteMap.read(reader
	}

