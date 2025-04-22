		try (PidLock lock = new PidLock()) {
			if (!lock.lock()) {
				return Collections.emptyList();
			}
			packRefs();
			Collection<Pack> newPacks = repack();
			prune(Collections.emptySet());
			return newPacks;
		}
