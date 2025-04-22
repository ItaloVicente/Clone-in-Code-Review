			final PackFileName name;
			try {
				name = new PackFileName(directory
			} catch (IllegalArgumentException e) {
				continue;
			}
			if (!indexName.equals(name.create(INDEX).getName())) {
