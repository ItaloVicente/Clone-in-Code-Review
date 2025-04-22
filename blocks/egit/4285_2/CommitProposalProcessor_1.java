			for (String message : messages)
				if (message.startsWith(prefix))
					proposals.add(new CompletionProposal(message,
							replacementOffset, replacementLength, message
									.length(), (Image) resourceManager
									.get(UIIcons.ELCL16_COMMENTS),
							escapeWhitespace(message), null, null));
		} else {
			for (String message : messages)
				proposals.add(new CompletionProposal(message, offset, 0,
						message.length(), (Image) resourceManager
								.get(UIIcons.ELCL16_COMMENTS),
						escapeWhitespace(message), null, null));
