
		private ByteArraySet fullpaths;

		private ByteArraySet prefixes;

		private byte[] max;

		private Group(final PathFilter[] pathFilters) {
			fullpaths = new ByteArraySet(pathFilters.length);
			prefixes = new ByteArraySet(pathFilters.length / 5);
			max = pathFilters[0].pathRaw;
			Hasher hasher = new Hasher(null, 0);
			for (PathFilter pf : pathFilters) {
				hasher.init(pf.pathRaw, pf.pathRaw.length);
				while (hasher.hasNext()) {
					int hash = hasher.nextHash();
					if (hasher.hasNext())
						prefixes.addIfAbsent(pf.pathRaw, hasher.length(), hash);
				}
				fullpaths.addIfAbsent(pf.pathRaw, pf.pathRaw.length,
						hasher.getHash());
				if (compare(max, pf.pathRaw) < 0)
					max = pf.pathRaw;
