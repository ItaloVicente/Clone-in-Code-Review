		boolean shownURI = false;
		for (final TrackingRefUpdate u : r.getTrackingRefUpdates()) {
			if (!verbose && u.getResult() == RefUpdate.Result.NO_CHANGE)
				continue;

			final char type = shortTypeOf(u.getResult());
			final String longType = longTypeOf(u);
			final String src = abbreviateRef(u.getRemoteName(), false);
			final String dst = abbreviateRef(u.getLocalName(), true);

			if (!shownURI) {
				out.format(CLIText.get().fromURI, tn.getURI());
