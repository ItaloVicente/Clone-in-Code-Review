	@Option(name = "--no-standard-notes"
	private boolean noStandardNotes;

	private List<String> additionalNoteRefs = new ArrayList<String>();

	@Option(name = "--show-notes"
	void addAdditionalNoteRef(String notesRef) {
		additionalNoteRefs.add(notesRef);
	}

