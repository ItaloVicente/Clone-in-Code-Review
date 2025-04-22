
package org.eclipse.jgit.merge;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.merge.MergeChunk.ConflictState;

class MergeFormatterPass {

	private final EolAwareOutputStream out;

	private final MergeResult<RawText> res;

	private final List<String> seqName;

	private final Charset charset;

	private final boolean threeWayMerge;


	MergeFormatterPass(OutputStream out
			List<String> seqName
		this.out = new EolAwareOutputStream(out);
		this.res = res;
		this.seqName = seqName;
		this.charset = charset;
		this.threeWayMerge = (res.getSequences().size() == 3);
	}

	void formatMerge() throws IOException {
		boolean missingNewlineAtEnd = false;
		for (MergeChunk chunk : res) {
			RawText seq = res.getSequences().get(chunk.getSequenceIndex());
			writeConflictMetadata(chunk);
			for (int i = chunk.getBegin(); i < chunk.getEnd(); i++)
				writeLine(seq
			missingNewlineAtEnd = seq.isMissingNewlineAtEnd();
		}
		if (lastConflictingName != null)
			writeConflictEnd();
		if (!missingNewlineAtEnd)
			out.beginln();
	}

	private void writeConflictMetadata(MergeChunk chunk) throws IOException {
		if (lastConflictingName != null
				&& chunk.getConflictState() != ConflictState.NEXT_CONFLICTING_RANGE) {
			writeConflictEnd();
		}
		if (chunk.getConflictState() == ConflictState.FIRST_CONFLICTING_RANGE) {
			writeConflictStart(chunk);
		} else if (chunk.getConflictState() == ConflictState.NEXT_CONFLICTING_RANGE) {
			writeConflictChange(chunk);
		}
	}

	private void writeConflictEnd() throws IOException {
		lastConflictingName = null;
	}

	private void writeConflictStart(MergeChunk chunk) throws IOException {
		lastConflictingName = seqName.get(chunk.getSequenceIndex());
	}

	private void writeConflictChange(MergeChunk chunk) throws IOException {
		lastConflictingName = seqName.get(chunk.getSequenceIndex());
				+ lastConflictingName);
	}

	private void writeln(String s) throws IOException {
		out.beginln();
	}

	private void writeLine(RawText seq
		out.beginln();
		seq.writeLine(out
		if (out.isBeginln())
			out.write('\n');
	}
}
