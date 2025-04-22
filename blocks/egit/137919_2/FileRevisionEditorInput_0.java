			String identifier;
			if (rev instanceof CommitFileRevision) {
				identifier = rev.getContentIdentifier().substring(0, 6);
			} else {
				identifier = rev.getContentIdentifier();
			}
