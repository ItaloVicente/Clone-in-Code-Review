			final String base = indexName.substring(0
			final List<PackExt> extensions = new ArrayList<PackExt>(
					PackExt.values().length);
			for (PackExt ext : PackExt.values()) {
				final String name = base + ext.getExtension();
				if (names.contains(name))
					extensions.add(ext);
			}

			if (!extensions.contains(PACK)) {
