		TagOperation operation = new TagOperation(repository)
				.setAnnotated(true)
				.setName(name)
				.setMessage(message)
				.setTarget(head);
		operation.execute(null);
