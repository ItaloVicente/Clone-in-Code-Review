	public List<RevCommit> getMergedInto(RevCommit needle
			throws IOException {
		List<RevCommit> mergedCommits = new ArrayList<>();
		final RevFilter oldRF = filter;
		final TreeFilter oldTF = treeFilter;
		try {
			finishDelayedFreeFlags();
			filter = RevFilter.ALL;
			treeFilter = TreeFilter.ALL;
			for (RevCommit c: haystacks) {
				resetRetain(RevFlag.UNINTERESTING);
				markStart(c);
				boolean commitFound = false;
				RevCommit next;
				while ((next = next()) != null) {
					if (References.isSameObject(next
						mergedCommits.add(c);
						commitFound = true;
						break;
					}
				}
				if(!commitFound){
					markUninteresting(c);
				}
			}
		} finally {
			reset(~freeFlags & APP_FLAGS);
			filter = oldRF;
			treeFilter = oldTF;
		}
		return mergedCommits;
	}

