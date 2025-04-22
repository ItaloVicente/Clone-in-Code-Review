
			byte[] rp = walker.getRawPath();
			Hasher hasher = new Hasher(rp
			while (hasher.hashNext()) {
				int hash = hasher.nextHash();
				if (fullpaths.contains(rp
