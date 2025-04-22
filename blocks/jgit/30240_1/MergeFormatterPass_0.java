
package org.eclipse.jgit.merge;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.merge.MergeChunk.ConflictState;

class MergeFormatterPass {

	private final OutputStream out;

	private final MergeResult<RawText> res;

	private final List<String> seqName;

	private final String charsetName;

	MergeFormatterPass(OutputStream out
			String charsetName) {
		this.out = out;
		this.res = res;
		this.seqName = seqName;
		this.charsetName = charsetName;
	}

	void formatMerge() throws IOException {
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

				lastConflictingName = seqName.get(chunk.getSequenceIndex());
			}
			for (int i = chunk.getBegin(); i < chunk.getEnd(); i++) {
				seq.writeLine(out
				out.write('\n');
			}
		}
		if (lastConflictingName != null) {
		}
	}
}
