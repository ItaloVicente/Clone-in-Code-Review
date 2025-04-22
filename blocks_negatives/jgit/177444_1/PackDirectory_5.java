		for (String indexName : names) {
				continue;
			}

			final String base = indexName.substring(0, indexName.length() - 3);
			int extensions = 0;
			for (PackExt ext : PackExt.values()) {
				if (names.contains(base + ext.getExtension())) {
					extensions |= ext.getBit();
				}
			}

			if ((extensions & PACK.getBit()) == 0) {
