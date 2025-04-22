	private static boolean askUser(CredentialsProvider provider, URIish uri,
			String prompt, String... messages) {
		List<CredentialItem> items = new ArrayList<>(messages.length + 1);
		for (String message : messages) {
			items.add(new CredentialItem.InformationalMessage(message));
		}
		if (prompt != null) {
			CredentialItem.YesNoType answer = new CredentialItem.YesNoType(
					prompt);
			items.add(answer);
			return provider.get(uri, items) && answer.getValue();
		} else {
			return provider.get(uri, items);
