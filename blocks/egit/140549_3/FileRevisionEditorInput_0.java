			String identifier;
			if (rev instanceof CommitFileRevision) {
				identifier = rev.getContentIdentifier().substring(0, 7);
			} else {
				identifier = rev.getContentIdentifier();
			}
