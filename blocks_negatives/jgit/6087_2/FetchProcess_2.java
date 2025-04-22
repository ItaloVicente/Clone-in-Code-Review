			final Repository db, final RevWalk walk, final RefSpec spec,
			final Ref localRef) throws TransportException {
		final String name = localRef.getName();
		try {
			final TrackingRefUpdate u = new TrackingRefUpdate(db, name, spec
					.getSource(), true, ObjectId.zeroId(), "deleted");
			result.add(u);
			if (transport.isDryRun()){
				return;
			}
			u.delete(walk);
			switch (u.getResult()) {
			case NEW:
			case NO_CHANGE:
			case FAST_FORWARD:
			case FORCED:
				break;
			default:
				throw new TransportException(transport.getURI(), MessageFormat.format(
						JGitText.get().cannotDeleteStaleTrackingRef2, name, u.getResult().name()));
			}
		} catch (IOException e) {
			throw new TransportException(transport.getURI(), MessageFormat.format(
					JGitText.get().cannotDeleteStaleTrackingRef, name), e);
		}
