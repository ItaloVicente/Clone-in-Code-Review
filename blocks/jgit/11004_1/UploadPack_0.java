	private void parseWants() throws IOException {
		AsyncRevObjectQueue q = walk.parseAny(wantIds
		try {
			List<RevCommit> checkReachable = null;
			RevObject obj;
			while ((obj = q.next()) != null) {
				if (!advertised.contains(obj)) {
					switch (requestPolicy) {
					case ADVERTISED:
					default:
						throw new PackProtocolException(MessageFormat.format(
								JGitText.get().wantNotValid
					case REACHABLE_COMMIT:
						if (!(obj instanceof RevCommit)) {
							throw new PackProtocolException(MessageFormat.format(
								JGitText.get().wantNotValid
						}
						if (checkReachable == null)
							checkReachable = new ArrayList<RevCommit>();
						checkReachable.add((RevCommit) obj);
						break;
					case ANY:
						break;
					}
				}
				want(obj);

				if (!(obj instanceof RevCommit))
					obj.add(SATISFIED);
				if (obj instanceof RevTag) {
					obj = walk.peel(obj);
					if (obj instanceof RevCommit)
						want(obj);
				}
			}
			if (checkReachable != null)
				checkNotAdvertisedWants(checkReachable);
			wantIds.clear();
		} catch (MissingObjectException notFound) {
			ObjectId id = notFound.getObjectId();
			throw new PackProtocolException(MessageFormat.format(
					JGitText.get().wantNotValid
		} finally {
			q.release();
		}
	}

	private void want(RevObject obj) {
		if (!obj.has(WANT)) {
			obj.add(WANT);
			wantAll.add(obj);
		}
	}

	private void checkNotAdvertisedWants(List<RevCommit> notAdvertisedWants)
