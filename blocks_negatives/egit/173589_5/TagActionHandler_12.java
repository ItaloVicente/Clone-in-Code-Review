		final TagBuilder tag = new TagBuilder();
		PersonIdent personIdent = new PersonIdent(repo);
		final String tagName = dialog.getTagName();

		tag.setTag(tagName);
		tag.setTagger(personIdent);
		tag.setMessage(dialog.getTagMessage());
