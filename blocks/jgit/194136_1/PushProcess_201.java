	private boolean isFastForward(ObjectId oldOid
			throws TransportException {
		try {
			RevObject oldRev = walker.parseAny(oldOid);
			RevObject newRev = walker.parseAny(newOid);
			if (!(oldRev instanceof RevCommit) || !(newRev instanceof RevCommit)
					|| !walker.isMergedInto((RevCommit) oldRev
							(RevCommit) newRev)) {
				return false;
			}
		} catch (MissingObjectException x) {
			return false;
		} catch (Exception x) {
			throw new TransportException(transport.getURI()
					MessageFormat.format(JGitText
							.get().readingObjectsFromLocalRepositoryFailed
							x.getMessage())
					x);
		}
		return true;
	}

	private Map<String
			throws TransportException {
		Map<String
		boolean hadMatch = false;
		for (RemoteRefUpdate update : toPush.values()) {
			if (update.isMatching()) {
				if (hadMatch) {
					throw new TransportException(MessageFormat.format(
							JGitText.get().duplicateRemoteRefUpdateIsIllegal
				}
				expandMatching(result
				hadMatch = true;
			} else if (result.put(update.getRemoteName()
				throw new TransportException(MessageFormat.format(
						JGitText.get().duplicateRemoteRefUpdateIsIllegal
						update.getRemoteName()));
			}
		}
		return result;
	}

	private void expandMatching(Map<String
			RemoteRefUpdate match) throws TransportException {
		try {
			Map<String
			Collection<RefSpec> fetchSpecs = match.getFetchSpecs();
			boolean forceUpdate = match.isForceUpdate();
			for (Ref local : transport.local.getRefDatabase()
					.getRefsByPrefix(Constants.R_HEADS)) {
				if (local.isSymbolic()) {
					continue;
				}
				String name = local.getName();
				Ref advertised = advertisement.get(name);
				if (advertised == null || advertised.isSymbolic()) {
					continue;
				}
				ObjectId oldOid = advertised.getObjectId();
				if (oldOid == null || ObjectId.zeroId().equals(oldOid)) {
					continue;
				}
				ObjectId newOid = local.getObjectId();
				if (newOid == null || ObjectId.zeroId().equals(newOid)) {
					continue;
				}

				RemoteRefUpdate rru = new RemoteRefUpdate(transport.local
						newOid
						Transport.findTrackingRefName(name
						oldOid);
				if (updates.put(rru.getRemoteName()
					throw new TransportException(MessageFormat.format(
							JGitText.get().duplicateRemoteRefUpdateIsIllegal
							rru.getRemoteName()));
				}
			}
		} catch (IOException x) {
			throw new TransportException(transport.getURI()
					MessageFormat.format(JGitText
							.get().readingObjectsFromLocalRepositoryFailed
							x.getMessage())
					x);
		}
	}

