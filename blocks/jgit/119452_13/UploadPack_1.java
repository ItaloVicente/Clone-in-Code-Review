
			Collection<Ref> allTags = refs == null ? null : refs.values();
			if (includeTag && allTags == null) {
				allTags = db.getRefDatabase().getRefsByPrefix("refs/tags/");
			}
			sendPack(new PackStatistics.Accumulator()
