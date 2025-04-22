	public List<Ref> getMergedInto(RevCommit needle
			throws IOException{
		return getMergedInto(needle
				GetMergedIntoStrategy.EVALUATE_ALL);
	}

	public boolean isMergedIntoAny(RevCommit needle
			throws IOException {
		return getMergedInto(needle
				GetMergedIntoStrategy.RETURN_ON_FIRST_FIND).size() > 0;
	}

	public boolean isMergedIntoAll(RevCommit needle
			throws IOException {
		return getMergedInto(needle
				GetMergedIntoStrategy.RETURN_ON_FIRST_NOT_FOUND).size()
				== haystacks.size();
	}

	private List<Ref> getMergedInto(RevCommit needle
			Enum returnStrategy) throws IOException {
		List<Ref> result = new ArrayList<>();
		final RevFilter oldRF = filter;
		final TreeFilter oldTF = treeFilter;
		try {
			finishDelayedFreeFlags();
			filter = RevFilter.ALL;
			treeFilter = TreeFilter.ALL;
			for (Ref r: haystacks) {
				RevObject o = parseAny(r.getObjectId());
				if (!(o instanceof RevCommit)) {
					continue;
				}
				RevCommit c = (RevCommit) o;
				resetRetain(RevFlag.UNINTERESTING);
				markStart(c);
				boolean commitFound = false;
				RevCommit next;
				while ((next = next()) != null) {
					if (References.isSameObject(next
						result.add(r);
						if (returnStrategy.equals(GetMergedIntoStrategy.RETURN_ON_FIRST_FIND)) {
							return result;
						}
						commitFound = true;
						break;
					}
				}
				if(!commitFound){
					markUninteresting(c);
					if (returnStrategy.equals(GetMergedIntoStrategy.RETURN_ON_FIRST_NOT_FOUND)) {
						return result;
					}
				}
			}
		} finally {
			reset(~freeFlags & APP_FLAGS);
			filter = oldRF;
			treeFilter = oldTF;
		}
		return result;
	}

