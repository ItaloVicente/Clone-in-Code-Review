		if (transport.isRemoveDeletedRefs())
			deleteStaleTrackingRefs(result, walk);
		for (TrackingRefUpdate u : localUpdates) {
			try {
				u.update(walk);
				result.add(u);
			} catch (IOException err) {
				throw new TransportException(MessageFormat.format(
						JGitText.get().failureUpdatingTrackingRef, u.getLocalName(), err.getMessage()), err);
