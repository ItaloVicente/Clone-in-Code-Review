	public List<Ref> getMergedInto(RevCommit commit
			throws IOException{
		return getMergedInto(commit
	}

	public boolean isMergedIntoAny(RevCommit commit
			throws IOException {
		return getMergedInto(commit
				GetMergedIntoStrategy.RETURN_ON_FIRST_FOUND).size() > 0;
	}

	public boolean isMergedIntoAll(RevCommit commit
			throws IOException {
		return getMergedInto(commit
				GetMergedIntoStrategy.RETURN_ON_FIRST_NOT_FOUND).size()
				== refs.size();
	}

	private List<Ref> getMergedInto(RevCommit needle
			Enum returnStrategy) throws IOException {
		List<Ref> result = new ArrayList<>();
		RevFilter oldRF = filter;
		TreeFilter oldTF = treeFilter;
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
						if (returnStrategy == GetMergedIntoStrategy.RETURN_ON_FIRST_FOUND) {
							return result;
						}
						commitFound = true;
						break;
					}
				}
				if(!commitFound){
					markUninteresting(c);
					if (returnStrategy == GetMergedIntoStrategy.RETURN_ON_FIRST_NOT_FOUND) {
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

