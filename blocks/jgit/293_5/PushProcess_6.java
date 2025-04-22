		for (final RemoteRefUpdate rru : toPush.values()) {
			final TrackingRefUpdate tru = rru.getTrackingRefUpdate();
			if (tru != null)
				res.add(tru);
		}
		return res;
