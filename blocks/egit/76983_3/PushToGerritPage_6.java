	private PushOperationUI getOperation()
			throws IOException, URISyntaxException {
		URIish uri = new URIish(uriCombo.getText());
		Ref currentHead = repository.exactRef(Constants.HEAD);
		String ref = prefixCombo.getItem(prefixCombo.getSelectionIndex())
				+ branchText.getText().trim();
		if (topicText.isEnabled()) {
			ref = setTopicInRef(ref, topicText.getText().trim());
		}
		RemoteRefUpdate update = new RemoteRefUpdate(repository, currentHead,
				ref, false, null, null);
		PushOperationSpecification spec = new PushOperationSpecification();

		spec.addURIRefUpdates(uri, Arrays.asList(update));
		return new PushOperationUI(repository, spec, false);
	}

