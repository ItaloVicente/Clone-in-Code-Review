		for (RevObject o : notAdvertisedWants) {
			if (!(o instanceof RevCommit)) {
				throw new PackProtocolException(MessageFormat.format(
						JGitText.get().wantNotValid,
						notAdvertisedWants.iterator().next().name()));
			}
			walk.markStart((RevCommit) o);
		}

