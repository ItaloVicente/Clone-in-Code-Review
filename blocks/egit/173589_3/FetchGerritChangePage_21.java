		TagOperation operation = new TagOperation(repository)
				.setAnnotated(true)
				.setName(textForTag)
				.setTagger(personIdent)
				.setMessage(NLS.bind(
						UIText.FetchGerritChangePage_GeneratedTagMessage,
						spec.getSource()))
				.setTarget(commit)
				.setSign(Boolean.FALSE);
		operation.execute(monitor);
