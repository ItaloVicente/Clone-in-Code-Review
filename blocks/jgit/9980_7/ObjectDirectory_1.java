			final String base = indexName.substring(0
			int extensions = 0;
			for (PackExt ext : PackExt.values()) {
				if (names.contains(base + ext.getExtension()))
					extensions |= ext.getBit();
			}

			if ((extensions & PACK.getBit()) == 0) {
