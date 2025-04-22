			try {
				PackFile pack = new PackFile(directory
				if (pack.getPackExt() != null) {
					Map<PackExt
							.get(pack.getId());
					if (packByExt == null) {
						packByExt = new EnumMap<>(PackExt.class);
						packFilesByExtById.put(pack.getId()
					}
					packByExt.put(pack.getPackExt()
				}
			} catch (IllegalArgumentException e) {
				continue;
