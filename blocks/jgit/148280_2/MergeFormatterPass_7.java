
	private boolean isBaseChunk(MergeChunk chunk) {
		return chunk.getConflictState() == ConflictState.BASE_CONFLICTING_RANGE;
	}

	private boolean isMineChunk(MergeChunk chunk) {
		return chunk
				.getConflictState() == ConflictState.FIRST_CONFLICTING_RANGE;
	}

	private boolean isYoursChunk(MergeChunk chunk) {
		return chunk.getConflictState() == ConflictState.NEXT_CONFLICTING_RANGE;
	}
