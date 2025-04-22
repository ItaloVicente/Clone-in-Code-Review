	public void addConflict(String path
		if (conflicts == null)
			conflicts = new HashMap<String
		conflicts.put(path
	}

	public void addConflict(String path
		if (conflicts == null)
			conflicts = new HashMap<String
		int nrOfConflicts = 0;
		for (MergeChunk mergeChunk : lowLevelResult) {
			if (mergeChunk.getConflictState().equals(ConflictState.FIRST_CONFLICTING_RANGE)) {
				nrOfConflicts++;
			}
		}
		int currentConflict = -1;
		int[][] ret=new int[nrOfConflicts][mergedCommits.length+1];
		for (MergeChunk mergeChunk : lowLevelResult) {
			int endOfChunk = 0;
			if (mergeChunk.getConflictState().equals(ConflictState.FIRST_CONFLICTING_RANGE)) {
				if (currentConflict > -1) {
					ret[currentConflict][mergedCommits.length] = endOfChunk;
				}
				currentConflict++;
				endOfChunk = mergeChunk.getEnd();
				ret[currentConflict][mergeChunk.getSequenceIndex()] = mergeChunk.getBegin();
			}
			if (mergeChunk.getConflictState().equals(ConflictState.NEXT_CONFLICTING_RANGE)) {
				if (mergeChunk.getEnd() > endOfChunk)
					endOfChunk = mergeChunk.getEnd();
				ret[currentConflict][mergeChunk.getSequenceIndex()] = mergeChunk.getBegin();
			}
		}
		conflicts.put(path
	}

