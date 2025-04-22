		int extensions = PACK.getBit() | INDEX.getBit();
		final String base = p.substring(0
		for (PackExt ext : PackExt.values()) {
			if ((extensions & ext.getBit()) == 0) {
				final String name = base + ext.getExtension();
				if (new File(pack.getParentFile()
					extensions |= ext.getBit();
			}
		}

		PackFile res = new PackFile(pack
