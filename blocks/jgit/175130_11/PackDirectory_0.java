			try {
				PackFile pack = new PackFile(directory
				Map<PackExt
						.get(pack.getId());
				if (packByExt == null) {
					packByExt = new HashMap<>(PackExt.values().length);
					packFilesByExtById.put(pack.getId()
				}
				packByExt.put(pack.getPackExt()
			} catch (IllegalArgumentException e) {
				continue;
