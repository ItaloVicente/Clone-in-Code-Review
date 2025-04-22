		private static boolean askUser(CredentialsProvider provider
				String prompt
			List<CredentialItem> items = new ArrayList<>(messages.length + 1);
			for (String message : messages) {
				items.add(new CredentialItem.InformationalMessage(message));
			}
			if (prompt != null) {
				CredentialItem.YesNoType answer = new CredentialItem.YesNoType(
						prompt);
				items.add(answer);
				return provider.get(uri
			} else {
				return provider.get(uri
			}
