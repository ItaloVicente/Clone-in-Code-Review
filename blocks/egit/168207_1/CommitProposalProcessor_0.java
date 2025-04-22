			if (offset >= selection.x && offset < selection.x + selection.y) {
				replacementLength = selection.x + selection.y - offset;
			} else {
				replacementLength = 0;
			}
			for (String message : messages) {
				proposals.add(new CompletionProposal(message, offset,
						replacementLength, message.length(),
						(Image) resourceManager.get(UIIcons.ELCL16_COMMENTS),
