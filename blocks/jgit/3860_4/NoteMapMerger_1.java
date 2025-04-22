		Merger m = nonNotesMergeStrategy.newMerger(db
		if (m instanceof ThreeWayMerger)
			((ThreeWayMerger) m).setBase(baseId);
		if (!m.merge(oursId
			throw new NotesMergeConflictException(baseList
					theirsList);
		ObjectId resultTreeId = m.getResultTreeId();
