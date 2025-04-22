	private boolean doRescanOnPackError(PackList pList
			IOException e) {
				+ p.getPackName());
		if (searchPacksAgain(pList
			int transientErrorCount = p.incrementTransientErrorCount();
			LOG.debug("found changed packlist
					+ transientErrorCount);
			if (transientErrorCount <= MAX_RESCANS) {
				return true;
			} else {
				LOG.error(MessageFormat.format(
						JGitText.get().assumePersistentPackProblem
						Integer.valueOf(MAX_RESCANS)
						p.getPackFile().getAbsolutePath()));
				handlePackError(e
			}
		} else {
			handlePackError(e
		}
		return false;
	}

