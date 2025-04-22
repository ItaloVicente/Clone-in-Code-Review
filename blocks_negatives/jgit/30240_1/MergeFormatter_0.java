		boolean threeWayMerge = (res.getSequences().size() == 3);
		for (MergeChunk chunk : res) {
			RawText seq = res.getSequences().get(chunk.getSequenceIndex());
			if (lastConflictingName != null
					&& chunk.getConflictState() != ConflictState.NEXT_CONFLICTING_RANGE) {
				lastConflictingName = null;
			}
			if (chunk.getConflictState() == ConflictState.FIRST_CONFLICTING_RANGE) {
				lastConflictingName = seqName.get(chunk.getSequenceIndex());
			} else if (chunk.getConflictState() == ConflictState.NEXT_CONFLICTING_RANGE) {

				/*
				 * In case of a non-three-way merge I'll add the name of the
				 * conflicting chunk behind the equal signs. I also append the
				 * name of the last conflicting chunk after the ending
				 * greater-than signs. If somebody knows a better notation to
				 * present non-three-way merges - feel free to correct here.
				 */
				lastConflictingName = seqName.get(chunk.getSequenceIndex());
			}
			for (int i = chunk.getBegin(); i < chunk.getEnd(); i++) {
				seq.writeLine(out, i);
				out.write('\n');
			}
		}
		if (lastConflictingName != null) {
		}
