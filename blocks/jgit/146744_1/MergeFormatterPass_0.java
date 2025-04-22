			if (!isBaseChunk(chunk) || writeBase) {
				RawText seq = res.getSequences().get(chunk.getSequenceIndex());
				writeConflictMetadata(chunk);
				for (int i = chunk.getBegin(); i < chunk.getEnd(); i++)
					writeLine(seq
				missingNewlineAtEnd = seq.isMissingNewlineAtEnd();
			}
