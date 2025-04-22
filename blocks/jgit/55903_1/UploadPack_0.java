		walk.assumeShallow(clientShallowCommits);
	}

	private void verifyClientShallow()
			throws IOException
		AsyncRevObjectQueue q = walk.parseAny(clientShallowCommits
		try {
			for (;;) {
				try {
					RevObject o = q.next();
					if (!(o instanceof RevCommit)) {
						throw new PackProtocolException(
							MessageFormat.format(
								JGitText.get().invalidShallowObject
								o.name()));
					}
				} catch (MissingObjectException notCommit) {
					clientShallowCommits.remove(notCommit.getObjectId());
					continue;
				}
			}
		} finally {
			q.release();
		}
