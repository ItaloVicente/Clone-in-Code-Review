		if (verboseBranchMode) {
			StringBuilder suffix = new StringBuilder();
			if (node.getCommitId() != null) {
				suffix.append(' ').append(
						abbreviate(ObjectId.fromString(node.getCommitId())));
			}
			String message = node.getShortMessage();
			if (!message.isEmpty()) {
				suffix.append(' ').append(message);
			}
			if (suffix.length() > 0) {
				decoration.addSuffix(suffix.toString());
			}
