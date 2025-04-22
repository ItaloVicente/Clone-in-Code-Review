			final String base = indexName.substring(0
			int extensions = 0;
			for (PackExt ext : PackExt.values()) {
				if (names.contains(base + ext.getExtension()))
					extensions |= 1 << ext.getPosition();
			}

			if ((extensions & 1 << PACK.getPosition()) == 0) {
