		final FetchResult r;
		try {
			r = tn.fetch(new TextProgressMonitor(), toget);
			if (r.getTrackingRefUpdates().isEmpty())
				return;
		} finally {
			tn.close();
		}
		showFetchResult(tn, r);
