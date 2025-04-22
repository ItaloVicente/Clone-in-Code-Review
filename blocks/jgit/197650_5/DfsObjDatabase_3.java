	private static Map<DfsPackDescription
			PackList old) {
		Map<DfsPackDescription
		for (DfsCommitGraph cg : old.commitGraphs) {
			if (!cg.invalid()) {
				forReuse.put(cg.desc
			}
		}
		return forReuse;
	}

