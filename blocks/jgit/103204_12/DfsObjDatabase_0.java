	void addReftable(DfsPackDescription add
			throws IOException {
		PackList o
		do {
			o = packList.get();
			if (o == NO_PACKS) {
				o = scanPacks(o);
				for (DfsReftable t : o.reftables) {
					if (t.getPackDescription().equals(add)) {
						return;
					}
				}
			}

			List<DfsReftable> tables = new ArrayList<>(1 + o.reftables.length);
			for (DfsReftable t : o.reftables) {
				if (!remove.contains(t.getPackDescription())) {
					tables.add(t);
				}
			}
			tables.add(new DfsReftable(add));
			n = new PackListImpl(o.packs
		} while (!packList.compareAndSet(o
	}

