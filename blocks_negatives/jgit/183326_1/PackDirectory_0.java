	Collection<Pack> getPacks() {
		PackList list = packList.get();
		if (list == NO_PACKS) {
			list = scanPacks(list);
		}
		Pack[] packs = list.packs;
		return Collections.unmodifiableCollection(Arrays.asList(packs));
	}
