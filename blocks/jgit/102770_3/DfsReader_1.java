		List<DfsPackFile> sorted = new ArrayList<>(packs.length);
		for (DfsPackFile p : packs) {
			if (p.getPackDescription().getPackSource() != UNREACHABLE_GARBAGE) {
				sorted.add(p);
			}
		}
		Collections.sort(sorted
