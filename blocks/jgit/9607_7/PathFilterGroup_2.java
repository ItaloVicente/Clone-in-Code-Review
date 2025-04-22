		private ByteArraySet fullpaths;

		private ByteArraySet prefixes;

		private byte[] max;

		private Group(final PathFilter[] pathFilters) {
			fullpaths = new ByteArraySet(pathFilters.length);
			prefixes = new ByteArraySet(pathFilters.length / 5);
			max = pathFilters[0].pathRaw;
			Hasher hasher = new Hasher(null
			for (PathFilter pf : pathFilters) {
				hasher.init(pf.pathRaw
				while (hasher.hasNext()) {
					int hash = hasher.nextHash();
					if (hasher.hasNext())
						prefixes.addIfAbsent(pf.pathRaw
				}
				fullpaths.addIfAbsent(pf.pathRaw
						hasher.getHash());
				if (compare(max
					max = pf.pathRaw;
			}
		}
