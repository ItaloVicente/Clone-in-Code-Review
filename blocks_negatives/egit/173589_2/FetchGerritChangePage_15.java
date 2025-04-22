		tag.setTag(textForTag);
		tag.setTagger(personIdent);
		tag.setMessage(NLS.bind(
				UIText.FetchGerritChangePage_GeneratedTagMessage,
				spec.getSource()));
		tag.setObjectId(commit);
		new TagOperation(repository, tag, false).execute(monitor);
