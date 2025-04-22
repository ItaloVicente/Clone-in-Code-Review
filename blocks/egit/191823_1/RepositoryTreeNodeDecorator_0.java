		if (verboseBranchMode) {
			String suffix = ""; //$NON-NLS-1$
			if (node.getCommitId() != null) {
				suffix += ' '
						+ abbreviate(ObjectId.fromString(node.getCommitId()));
			}
			String message = node.getShortMessage();
			if (!message.isEmpty()) {
				suffix += ' ' + message;
			}
			if (!suffix.isEmpty()) {
				decoration.addSuffix(suffix);
			}
