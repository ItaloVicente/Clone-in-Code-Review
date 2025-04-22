		final PackWriter pw;
		pw = new PackWriter(db, pm, NullProgressMonitor.INSTANCE);
		pw.setDeltaBaseAsOffset(options.contains(OPTION_OFS_DELTA));
		pw.setThin(thin);
		pw.preparePack(wantAll, commonBase);
		if (options.contains(OPTION_INCLUDE_TAG)) {
			for (final Ref r : refs.values()) {
				final RevObject o;
				try {
					o = walk.parseAny(r.getObjectId());
				} catch (IOException e) {
					continue;
