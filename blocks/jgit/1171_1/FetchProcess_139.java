		try {
			if (transport.isRemoveDeletedRefs())
				deleteStaleTrackingRefs(result
			for (TrackingRefUpdate u : localUpdates) {
				try {
					u.update(walk);
					result.add(u);
				} catch (IOException err) {
					throw new TransportException(MessageFormat.format(JGitText
							.get().failureUpdatingTrackingRef
							u.getLocalName()
				}
