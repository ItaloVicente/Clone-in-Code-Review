	private void commitNoteMap(RevWalk walk, NoteMap map,
			RevCommit notesCommit,
			ObjectInserter inserter,
			String msg)
			throws IOException {
		CommitBuilder builder = new CommitBuilder();
		builder.setTreeId(map.writeTree(inserter));
		builder.setAuthor(new PersonIdent(repo));
		builder.setCommitter(builder.getAuthor());
		builder.setMessage(msg);
		if (notesCommit != null)
			builder.setParentIds(notesCommit);
		ObjectId commit = inserter.insert(builder);
		inserter.flush();
		RefUpdate refUpdate = repo.updateRef(notesRef);
		if (notesCommit != null)
			refUpdate.setExpectedOldObjectId(notesCommit);
		else
			refUpdate.setExpectedOldObjectId(ObjectId.zeroId());
		refUpdate.setNewObjectId(commit);
		refUpdate.update(walk);
	}

