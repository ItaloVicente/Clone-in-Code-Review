
	private PushResult prepareOperationResult() {
		final PushResult result = new PushResult();
		result.setAdvertisedRefs(transport.getURI(), connection.getRefsMap());
		result.setRemoteUpdates(toPush);

		for (final RemoteRefUpdate rru : toPush.values()) {
			final TrackingRefUpdate tru = rru.getTrackingRefUpdate();
			if (tru != null)
				result.add(tru);
		}
		return result;
	}
