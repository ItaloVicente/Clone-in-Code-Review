			List<ICommitMessageProvider> messageProviders = getCommitMessageProviders();
			IResource[] resourcesArray = resources
					.toArray(new IResource[0]);
			String providedMessageSeparator = "\n\n"; //$NON-NLS-1$

			for (ICommitMessageProvider messageProvider : messageProviders) {
				String message = null;
				try {
					message = messageProvider.getMessage(resourcesArray);
				} catch (RuntimeException e) {
					Activator.logError(e.getMessage(), e);
				}

				if (message != null && !"".equals(message.trim())) { //$NON-NLS-1$
					if (calculatedCommitMessage.length() > 0) {
						calculatedCommitMessage
								.append(providedMessageSeparator);
					}
					calculatedCommitMessage.append((message.trim()));
				}
