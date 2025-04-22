		boolean isNew = toInsert.add(SEEN);
		if (!isNew) {

			for (Candidate p = queue; p != null; p = p.queueNext) {
				if (p.sourceCommit.equals(toInsert.sourceCommit)) {
					p.regionList = Region.merge(p.regionList
							toInsert.regionList);
					return;
				}
			}
		}
