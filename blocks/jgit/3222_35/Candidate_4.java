
package org.eclipse.jgit.blame;

import java.io.IOException;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

public class BlameResult {
	public static BlameResult create(BlameGenerator gen) throws IOException {
		String path = gen.getResultPath();
		RawText contents = gen.getResultContents();
		if (contents == null) {
			gen.release();
			return null;
		}
		return new BlameResult(gen
	}

	private final String resultPath;

	private final RevCommit[] sourceCommits;

	private final PersonIdent[] sourceAuthors;

	private final PersonIdent[] sourceCommitters;

	private final String[] sourcePaths;

	private final int[] sourceLines;

	private RawText resultContents;

	private BlameGenerator generator;

	private int lastLength;

	BlameResult(BlameGenerator bg
		generator = bg;
		resultPath = path;
		resultContents = text;

		int cnt = text.size();
		sourceCommits = new RevCommit[cnt];
		sourceAuthors = new PersonIdent[cnt];
		sourceCommitters = new PersonIdent[cnt];
		sourceLines = new int[cnt];
		sourcePaths = new String[cnt];
	}

	public String getResultPath() {
		return resultPath;
	}

	public RawText getResultContents() {
		return resultContents;
	}

	public void discardResultContents() {
		resultContents = null;
	}

	public boolean hasSourceData(int idx) {
		return sourceLines[idx] != 0;
	}

	public boolean hasSourceData(int start
		for (; start < end; start++)
			if (sourceLines[start] == 0)
				return false;
		return true;
	}

	public RevCommit getSourceCommit(int idx) {
		return sourceCommits[idx];
	}

	public PersonIdent getSourceAuthor(int idx) {
		return sourceAuthors[idx];
	}

	public PersonIdent getSourceCommitter(int idx) {
		return sourceCommitters[idx];
	}

	public String getSourcePath(int idx) {
		return sourcePaths[idx];
	}

	public int getSourceLine(int idx) {
		return sourceLines[idx] - 1;
	}

	public void computeAll() throws IOException {
		BlameGenerator gen = generator;
		if (gen == null)
			return;

		try {
			while (gen.next())
				loadFrom(gen);
		} finally {
			gen.release();
			generator = null;
		}
	}

	public int computeNext() throws IOException {
		BlameGenerator gen = generator;
		if (gen == null)
			return -1;

		if (gen.next()) {
			loadFrom(gen);
			lastLength = gen.getRegionLength();
			return gen.getResultStart();
		} else {
			gen.release();
			generator = null;
			return -1;
		}
	}

	public int lastLength() {
		return lastLength;
	}

	public void computeRange(int start
		BlameGenerator gen = generator;
		if (gen == null)
			return;

		while (start < end) {
			if (hasSourceData(start
				return;

			if (!gen.next()) {
				gen.release();
				generator = null;
				return;
			}

			loadFrom(gen);


			int resLine = gen.getResultStart();
			int resEnd = gen.getResultEnd();

			if (resLine <= start && start < resEnd)
				start = resEnd;

			if (resLine <= end && end < resEnd)
				end = resLine;
		}
	}

	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("BlameResult: ");
		r.append(getResultPath());
		return r.toString();
	}

	private void loadFrom(BlameGenerator gen) {
		RevCommit srcCommit = gen.getSourceCommit();
		PersonIdent srcAuthor = gen.getSourceAuthor();
		PersonIdent srcCommitter = gen.getSourceCommitter();
		String srcPath = gen.getSourcePath();
		int srcLine = gen.getSourceStart();
		int resLine = gen.getResultStart();
		int resEnd = gen.getResultEnd();

		for (; resLine < resEnd; resLine++) {
			sourceCommits[resLine] = srcCommit;
			sourceAuthors[resLine] = srcAuthor;
			sourceCommitters[resLine] = srcCommitter;
			sourcePaths[resLine] = srcPath;

			sourceLines[resLine] = ++srcLine;
		}
	}
}
