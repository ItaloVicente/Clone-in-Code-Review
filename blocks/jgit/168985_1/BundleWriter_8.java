			if (cachedPacks.isEmpty()) {
				HashSet<ObjectId> inc = new HashSet<>();
				HashSet<ObjectId> exc = new HashSet<>();
				inc.addAll(include.values());
				for (RevCommit r : assume) {
					exc.add(r.getId());
				}
				if (exc.isEmpty()) {
					packWriter.setTagTargets(tagTargets);
				}
				packWriter.setThin(!exc.isEmpty());
				packWriter.preparePack(monitor
			} else {
				packWriter.preparePack(cachedPacks);
