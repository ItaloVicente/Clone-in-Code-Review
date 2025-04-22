		org.eclipse.jgit.lib.Tag tag = new org.eclipse.jgit.lib.Tag(db);
		tag.setObjId(object);
		tag.setType(Constants.typeString(ldr.getType()));
		tag.setTagger(new PersonIdent(db));
		tag.setMessage(message.replaceAll("\r", ""));
		tag.setTag(tagName.substring(Constants.R_TAGS.length()));
		tag.tag();
