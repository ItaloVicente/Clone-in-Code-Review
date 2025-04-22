		final ObjectLoader ldr = db.open(object);
		final ObjectInserter inserter = db.newObjectInserter();
		final ObjectId id;
		try {
			final org.eclipse.jgit.lib.TagBuilder tag;

			tag = new org.eclipse.jgit.lib.TagBuilder();
			tag.setObjectId(object, ldr.getType());
			tag.setTagger(new PersonIdent(db));
			tag.setMessage(message.replaceAll("\r", ""));
			tag.setTag(shortName);
			id = inserter.insert(tag);
			inserter.flush();
		} finally {
			inserter.release();
		}

		RefUpdate ru = db.updateRef(tagName);
		ru.setForceUpdate(force);
		ru.setNewObjectId(id);
		ru.setRefLogMessage("tagged " + shortName, false);
		switch (ru.update()) {
		case NEW:
		case FAST_FORWARD:
		case FORCED:
			break;

		case REJECTED:
			throw die(MessageFormat.format(CLIText.get().fatalErrorTagExists,
					shortName));

		default:
			throw die(MessageFormat.format(CLIText.get().failedToLockTag,
					shortName, ru.getResult()));
		}
