			boolean fastForward = true;
			try {
				RevObject oldRev = walker.parseAny(advertisedOld);
				final RevObject newRev = walker.parseAny(rru.getNewObjectId());
				if (!(oldRev instanceof RevCommit)
						|| !(newRev instanceof RevCommit)
						|| !walker.isMergedInto((RevCommit) oldRev,
								(RevCommit) newRev))
					fastForward = false;
			} catch (MissingObjectException x) {
				fastForward = false;
			} catch (Exception x) {
				throw new TransportException(transport.getURI(), MessageFormat.format(
						JGitText.get().readingObjectsFromLocalRepositoryFailed, x.getMessage()), x);
			}
