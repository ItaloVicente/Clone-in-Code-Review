		TagOperation operation = new TagOperation(repository.getRepository())
				.setAnnotated(true)
				.setName(name)
				.setMessage(message)
				.setTarget(head)
				.setTagger(new PersonIdent(repository.getRepository()));
		operation.execute(monitor);
