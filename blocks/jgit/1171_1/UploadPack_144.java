		final PackWriter pw = new PackWriter(db
		try {
			pw.setDeltaBaseAsOffset(options.contains(OPTION_OFS_DELTA));
			pw.setThin(thin);
			pw.preparePack(pm
			if (options.contains(OPTION_INCLUDE_TAG)) {
				for (final Ref r : refs.values()) {
					final RevObject o;
					try {
						o = walk.parseAny(r.getObjectId());
					} catch (IOException e) {
						continue;
					}
					if (o.has(WANT) || !(o instanceof RevTag))
						continue;
					final RevTag t = (RevTag) o;
					if (!pw.willInclude(t) && pw.willInclude(t.getObject()))
						pw.addObject(t);
