		if (toInsert.has(SEEN)) {
			for (Candidate p = queue; p != null; p = p.queueNext) {
				if (p.canMergeRegions(toInsert)) {
					p.mergeRegions(toInsert);
					return;
				}
			}
		}
