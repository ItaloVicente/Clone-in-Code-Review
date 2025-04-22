			refdb.commitPackedRefs(packedRefsLock

			if (refCache.isPresent()) {
				RefCache cache = refCache.get();
				Iterable<Entry<String
				for (Entry<String
					cache.insert(e.getValue());
				}
				List<String> deletedRefs = updates.getDeletions();
				for (String r : deletedRefs) {
					cache.delete(r);
				}
			}
