
		map = NoteMap.read(reader
		List<ObjectId> toRemove = new ArrayList<>();
		for (Note note : map) {
			toRemove.add(note.copy());
		}
		for (ObjectId objId : toRemove) {
			map.remove(objId);
		}
		assertFalse("is not empty"
		n = commitNoteMap(map);
		assertFalse("is empty"
		n = tr.getRevWalk().parseCommit(
				tr.commit().parent(n)
					.rm(".gitignore")
					.rm("zoo-animals.txt")
					.create());
		map = NoteMap.read(reader
		assertTrue("is empty"
