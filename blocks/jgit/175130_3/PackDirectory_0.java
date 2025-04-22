		Map<String
		for (String fileName : names) {
			try {
				PackFileName name = new PackFileName(directory
				Map<PackExt
						.get(name.getId());
				if (nameByExt == null) {
					nameByExt = new HashMap<>();
					packFileNamesByExtById.put(name.getId()
				}
				nameByExt.put(name.getPackExt()
			} catch (IllegalArgumentException e) {
