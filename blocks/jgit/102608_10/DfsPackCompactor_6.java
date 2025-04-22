	private Collection<DfsPackDescription> toPrune() {
		Set<DfsPackDescription> packs = new HashSet<>();
		for (DfsPackFile pack : srcPacks) {
			packs.add(pack.getPackDescription());
		}

		Set<DfsPackDescription> reftables = new HashSet<>();
		for (DfsReftable table : srcReftables) {
			reftables.add(table.getPackDescription());
		}

		for (Iterator<DfsPackDescription> i = packs.iterator(); i.hasNext();) {
			DfsPackDescription d = i.next();
			if (d.hasFileExt(REFTABLE) && !reftables.contains(d)) {
				i.remove();
			}
		}

		for (Iterator<DfsPackDescription> i = reftables.iterator();
				i.hasNext();) {
			DfsPackDescription d = i.next();
			if (d.hasFileExt(PACK) && !packs.contains(d)) {
				i.remove();
			}
		}

		Set<DfsPackDescription> toPrune = new HashSet<>();
		toPrune.addAll(packs);
		toPrune.addAll(reftables);
		return toPrune;
