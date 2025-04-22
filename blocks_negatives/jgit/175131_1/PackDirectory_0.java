		for (String indexName : names) {
			final PackFileName name;
			try {
				name = new PackFileName(directory, indexName);
			} catch (IllegalArgumentException e) {
				continue;
			}
			if (!indexName.equals(name.create(INDEX).getName())) {
