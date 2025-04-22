
package org.eclipse.jgit.merge;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.merge.MergeChunk.ConflictState;

public class MergeFormatter {
	public void formatMerge(OutputStream out
			List<String> seqName) throws IOException {
		String lastConflictingName="";
		boolean inConflict = false;
		for (MergeChunk chunk : res) {
			chunk.getBegin();
			RawText seq = (RawText) res.getSequences().get(chunk.getSequenceIndex());
			if (inConflict
					&& chunk.getConflictState() != ConflictState.NEXT_CONFLICTING_RANGE) {
				out.write((">>>>>>> "
								+ lastConflictingName + "\n")
								.getBytes());
				inConflict = false;
			}
			if (chunk.getConflictState() == ConflictState.FIRST_CONFLICTING_RANGE) {
				out.write(("<<<<<<< "
								+ seqName.get(chunk.getSequenceIndex()) + "\n")
								.getBytes());
				inConflict = true;
				lastConflictingName = seqName.get(chunk.getSequenceIndex());
			} else if (chunk.getConflictState() == ConflictState.NEXT_CONFLICTING_RANGE) {
				out.write("=======\n".getBytes());
				lastConflictingName = seqName.get(chunk.getSequenceIndex());
			}
			if (chunk.getConflictState() == ConflictState.NO_CONFLICT) {
				inConflict = false;
			}
			for (int i = chunk.getBegin(); i < chunk.getEnd(); i++) {
				seq.writeLine(out
				out.write(10);
			}
		}
		if (inConflict) {
			out.write((">>>>>>> "
					+ lastConflictingName + "\n")
					.getBytes());
			inConflict = false;
		}
	}

	public void formatMerge(OutputStream out
			String baseName
		List<String> names=new ArrayList<String>(3);
		names.add(baseName);
		names.add(oursName);
		names.add(theirsName);
		formatMerge(out
	}
}
