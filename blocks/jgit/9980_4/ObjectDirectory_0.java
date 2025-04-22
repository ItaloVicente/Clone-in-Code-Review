		int extensions = 1 << PACK.getPosition() | 1 << INDEX.getPosition();
		final String base = p.substring(0
		for (PackExt ext : PackExt.values()) {
			int mask = 1 << ext.getPosition();
			if ((extensions & mask) == 0) {
				final String name = base + ext.getExtension();
				if (new File(pack.getParentFile()
					extensions |= mask;
			}
		}

		PackFile res = new PackFile(pack
