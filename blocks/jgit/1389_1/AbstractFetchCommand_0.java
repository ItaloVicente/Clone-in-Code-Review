		ObjectReader reader = db.newObjectReader();
		try {
			boolean shownURI = false;
			for (final TrackingRefUpdate u : r.getTrackingRefUpdates()) {
				if (!verbose && u.getResult() == RefUpdate.Result.NO_CHANGE)
					continue;

				final char type = shortTypeOf(u.getResult());
				final String longType = longTypeOf(reader
				final String src = abbreviateRef(u.getRemoteName()
				final String dst = abbreviateRef(u.getLocalName()

				if (!shownURI) {
					out.format(CLIText.get().fromURI
					out.println();
					shownURI = true;
				}

				out.format(" %c %-17s %-10s -> %s"
