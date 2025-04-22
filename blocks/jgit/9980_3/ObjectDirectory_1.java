			final String base = indexName.substring(0
			final List<PackExt> extensions = new ArrayList<PackExt>(
					PackExt.values().length);
			for (PackExt ext : PackExt.values()) {
				if (names.contains(base + ext.getExtension()))
					extensions.add(ext);
			}

			if (!extensions.contains(PACK)) {
