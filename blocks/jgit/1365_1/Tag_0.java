		final ObjectInserter inserter = db.newObjectInserter();
		final ObjectId id;
		try {
			org.eclipse.jgit.lib.Tag tag = new org.eclipse.jgit.lib.Tag();
			tag.setObjectId(object
			tag.setTagger(new PersonIdent(db));
			tag.setMessage(message.replaceAll("\r"
			tag.setTag(shortName);
			id = inserter.insert(Constants.OBJ_TAG
			inserter.flush();
		} finally {
			inserter.release();
		}

		RefUpdate ru = db.updateRef(tagName);
		ru.setForceUpdate(force);
		ru.setNewObjectId(id);
		ru.setRefLogMessage("tagged " + shortName
		switch (ru.update()) {
		case NEW:
		case FAST_FORWARD:
		case FORCED:
			break;
