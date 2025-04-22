			List<ICommitMessageProvider> messageProviders = getCommitMessageProviders();
			IResource[] resourcesArray = resources
					.toArray(new IResource[0]);
			String providedMessageSeparator = "\n\n"; //$NON-NLS-1$
			for (ICommitMessageProvider messageProvider : messageProviders) {
				String message = messageProvider.getMessage(resourcesArray);
				if ((message != null) && (!"".equals(message.trim()))) { //$NON-NLS-1$
					calculatedCommitMessage.append(providedMessageSeparator)
							.append((message.trim()));
				}
			}
			if (calculatedCommitMessage.length() >= providedMessageSeparator
					.length()) {
				calculatedCommitMessage.delete(0,
						providedMessageSeparator.length());
