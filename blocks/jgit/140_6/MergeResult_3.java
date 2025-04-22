
package org.eclipse.jgit.merge;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.merge.MergeChunk.ConflictState;

public class MergeFormatter {
	public void formatMerge(OutputStream out
			List<String> seqName
		boolean threeWayMerge = (res.getSequences().size() == 3);
		for (MergeChunk chunk : res) {
			RawText seq = (RawText) res.getSequences().get(
					chunk.getSequenceIndex());
			if (lastConflictingName != null
					&& chunk.getConflictState() != ConflictState.NEXT_CONFLICTING_RANGE) {
				out.write((">>>>>>> " + lastConflictingName + "\n").getBytes(charsetName));
				lastConflictingName = null;
			}
			if (chunk.getConflictState() == ConflictState.FIRST_CONFLICTING_RANGE) {
				out.write(("<<<<<<< " + seqName.get(chunk.getSequenceIndex()) +
						"\n").getBytes(charsetName));
				lastConflictingName = seqName.get(chunk.getSequenceIndex());
			} else if (chunk.getConflictState() == ConflictState.NEXT_CONFLICTING_RANGE) {

				lastConflictingName = seqName.get(chunk.getSequenceIndex());
				out.write((threeWayMerge ? "=======\n" : "======= "
						+ lastConflictingName + "\n").getBytes(charsetName));
			}
			for (int i = chunk.getBegin(); i < chunk.getEnd(); i++) {
				seq.writeLine(out
				out.write('\n');
			}
		}
		if (lastConflictingName != null) {
			out.write((">>>>>>> " + lastConflictingName + "\n").getBytes(charsetName));
		}
	}

	public void formatMerge(OutputStream out
			String oursName
		List<String> names = new ArrayList<String>(3);
		names.add(baseName);
		names.add(oursName);
		names.add(theirsName);
		formatMerge(out
	}
}
