			try {
				PackFileName pack = new PackFileName(directory
				Map<PackExt
						.get(pack.getId());
				if (packByExt == null) {
					packByExt = new HashMap<>();
					packFileNamesByExtById.put(pack.getId()
				}
				packByExt.put(pack.getPackExt()
			} catch (IllegalArgumentException e) {
				continue;
