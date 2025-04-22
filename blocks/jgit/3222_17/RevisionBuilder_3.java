package org.eclipse.jgit.blame;

import java.io.IOException;
import java.nio.charset.Charset;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

public class Revision {

	private static class LineText extends RawText {

		private static String getContent(byte[] input
				int number) {
			return new LineText(input
		}

		private Charset charset;

		public LineText(byte[] input
			super(input);
			this.charset = charset;
		}

		protected String decode(int start
			return RawParseUtils.decode(charset
		}

	}

	private String path;

	private RevCommit commit;

	private ObjectId blob;

	private Line[] lines;

	private int index;

	public Revision(String path
		this.path = path;
		this.lines = new Line[size];
	}

	public int hashCode() {
		return commit.hashCode() ^ blob.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		else if (obj instanceof Revision) {
			Revision other = (Revision) obj;
			return commit.equals(other.commit) && blob.equals(other.blob)
					&& this.path.equals(other.path);
		} else
			return false;
	}

	public String toString() {
		return this.path + "@" + this.commit.name();
	}

	public String getPath() {
		return this.path;
	}

	public RevCommit getCommit() {
		return this.commit;
	}

	Revision setCommit(RevCommit commit) {
		this.commit = commit;
		return this;
	}

	Revision setBlob(ObjectId blob) {
		this.blob = blob;
		return this;
	}

	public ObjectId getBlob() {
		return this.blob;
	}

	Revision addLine(Line line) {
		if (line != null) {
			lines[index] = line.setNumber(index).setStart(commit);
			index++;
		}
		return this;
	}

	public int getLineCount() {
		return this.lines.length;
	}

	public Line getLine(int lineNumber) {
		return lineNumber >= 0 && lineNumber < this.lines.length ? this.lines[lineNumber]
				: null;
	}

	public Line[] getLines() {
		return this.lines;
	}

	public String getLineContent(Repository repository
			throws IOException {
		return getLineContent(repository
	}

	public String getLineContent(Repository repository
			Charset charset) throws IOException {
		Line line = getLine(lineNumber);
		if (line == null)
			return null;
		ObjectLoader loader = repository.open(blob
		byte[] input;
		if (loader.isLarge())
			input = IO.readWholeStream(loader.openStream()
					(int) loader.getSize()).array();
		else
			input = loader.getCachedBytes();
		return LineText.getContent(input
	}
}
