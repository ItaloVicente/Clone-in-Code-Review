		for (RevObject obj : notAdvertisedWants) {
			if (!(obj instanceof RevCommit))
				throw new PackProtocolException(MessageFormat.format(
					JGitText.get().wantNotValid
			walk.markStart((RevCommit) obj);
		}
