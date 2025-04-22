			refdb.commitPackedRefs(packedRefsLock

			if (refCache.isPresent()) {
				RefCache cache = refCache.get();
				Iterator<Ref> loader = newRefs.iterator();
				while (loader.hasNext()) {
					cache.insert(loader.next());
				}
				List<String> deletedRefs = updates.deletions;
				for (String r : deletedRefs) {
					cache.delete(r);
				}
			}
