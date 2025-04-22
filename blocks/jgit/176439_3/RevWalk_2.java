	public List<RevCommit> getMergedInto(RevCommit needle
			throws IOException{
		return getMergedInto(needle
	}

	public boolean isMergedIntoAny(RevCommit needle
			throws IOException {
		if(getMergedInto(needle
			return true;
		}
		return false;
	}

	public boolean isMergedIntoAll(RevCommit needle
			throws IOException {
		if(getMergedInto(needle
				== haystacks.size()) {
			return true;
		}
		return false;
	}

	private List<RevCommit> getMergedInto(RevCommit needle
			Enum returnStrategy) throws IOException {
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
						if (returnStrategy.equals(GetMergedIntoStrategy.RETURN_ON_FIRST_FIND)) {
							return mergedCommits;
						}
						commitFound = true;
						break;
					}
				}
				if(!commitFound){
					markUninteresting(c);
					if (returnStrategy.equals(GetMergedIntoStrategy.RETURN_ON_FIRST_NOT_FOUND)) {
						return mergedCommits;
					}
				}
			}
		} finally {
			reset(~freeFlags & APP_FLAGS);
			filter = oldRF;
			treeFilter = oldTF;
		}
		return mergedCommits;
	}

