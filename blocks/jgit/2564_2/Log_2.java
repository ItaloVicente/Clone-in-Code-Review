			if (!noStandardNotes || additionalNoteRefs != null) {
				reader = db.newObjectReader();
				revWalk = new RevWalk(db);
				noteMaps = new LinkedHashMap<String
				if (!noStandardNotes) {
					noteMaps.put(Constants.R_NOTES_COMMITS
							getNoteMap(Constants.R_NOTES_COMMITS));
				}
				if (additionalNoteRefs != null) {
					for (String notesRef : additionalNoteRefs) {
						if (!notesRef.startsWith(Constants.R_NOTES)) {
							notesRef = Constants.R_NOTES + notesRef;
						}
						noteMaps.put(notesRef
					}
				}
			}

