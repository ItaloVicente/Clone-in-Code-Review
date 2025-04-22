		ObjectId resultTreeId;
		if (nonNotesMergeStrategy instanceof ThreeWayMergeStrategy) {
			ThreeWayMerger m = ((ThreeWayMergeStrategy) nonNotesMergeStrategy)
					.newMerger(db, true);
			m.setBase(baseId);
			if (!m.merge(oursId, theirsId))
				throw new NotesMergeConflictException(baseList, oursList,
						theirsList);

			resultTreeId = m.getResultTreeId();
		} else {
			Merger m = nonNotesMergeStrategy.newMerger(db, true);
			if (!m.merge(new AnyObjectId[] { oursId, theirsId }))
				throw new NotesMergeConflictException(baseList, oursList,
						theirsList);
			resultTreeId = m.getResultTreeId();
		}
