			if (message != null && !message.trim().isEmpty()) {
				if (calculatedCommitMessage.length() > 0) {
					calculatedCommitMessage.append(providedMessageSeparator);
				}
				calculatedCommitMessage.append((message.trim()));
			}
