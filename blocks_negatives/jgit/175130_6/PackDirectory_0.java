		for (String indexName : names) {
				continue;
			}

			final String base = indexName.substring(0, indexName.length() - 3);

			if (!names.contains(base + PACK.getExtension())) {
