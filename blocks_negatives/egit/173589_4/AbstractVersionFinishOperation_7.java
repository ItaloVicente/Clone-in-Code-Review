		TagBuilder tag = new TagBuilder();
		tag.setTag(name);
		tag.setTagger(new PersonIdent(repository.getRepository()));
		tag.setMessage(message);
		tag.setObjectId(head);
		new TagOperation(repository.getRepository(), tag, false)
				.execute(monitor);
