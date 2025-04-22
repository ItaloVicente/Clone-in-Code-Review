		if (prompt != null) {
			CredentialItem.YesNoType answer = new CredentialItem.YesNoType(
					prompt);
			items.add(answer);
			return provider.get(uri, items) && answer.getValue();
		} else {
			return provider.get(uri, items);
