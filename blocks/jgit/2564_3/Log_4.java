	private boolean showNotes(RevCommit c) throws IOException {
		if (noteMaps == null)
			return false;

		boolean printEmptyLine = false;
		boolean atLeastOnePrinted = false;
		for (Map.Entry<String
			String label = null;
			String notesRef = e.getKey();
			if (! notesRef.equals(Constants.R_NOTES_COMMITS)) {
				if (notesRef.startsWith(Constants.R_NOTES))
					label = notesRef.substring(Constants.R_NOTES.length());
				else
					label = notesRef;
			}
			boolean printedNote = showNotes(c
					printEmptyLine);
			atLeastOnePrinted = atLeastOnePrinted || printedNote;
			printEmptyLine = printedNote;
		}
		return atLeastOnePrinted;
	}

	private boolean showNotes(RevCommit c
			boolean emptyLine)
			throws IOException {
		ObjectId blobId = map.get(c);
		if (blobId == null)
			return false;
		if (emptyLine)
			out.println();
		out.print("Notes");
		if (label != null) {
			out.print(" (");
			out.print(label);
			out.print(")");
		}
		out.println(":");
		RawText rawText = new RawText(reader.open(blobId).getBytes());
		String s = rawText.getString(0
		final String[] lines = s.split("\n");
		for (final String l : lines) {
			out.print("    ");
			out.println(l);
		}
		return true;
	}

