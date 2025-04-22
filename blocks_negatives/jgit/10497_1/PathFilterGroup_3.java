
			byte[] rp = walker.getRawPath();
			Hasher hasher = new Hasher(rp, walker.getPathLength());
			while (hasher.hasNext()) {
				int hash = hasher.nextHash();
				if (fullpaths.contains(rp, hasher.length(), hash))
