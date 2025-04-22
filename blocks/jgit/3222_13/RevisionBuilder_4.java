package org.eclipse.jgit.blame;

import java.io.IOException;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.IO;

public class Revision {

	private String path;

	private int number;

	private RevCommit commit;

	private ObjectId blob;

	private Line[] lines;

	private int index;

	public Revision(String path
		this.path = path;
		this.lines = new Line[size];
	}

	@Override
	public int hashCode() {
		return commit.hashCode() ^ blob.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		else if (obj instanceof Revision) {
			Revision other = (Revision) obj;
			return this.number == other.number && this.path.equals(other.path);
		} else
			return false;
	}

	@Override
	public String toString() {
		return this.path + "#" + this.number;
	}

	public String getPath() {
		return this.path;
	}

	public RevCommit getCommit() {
		return this.commit;
	}

	public Revision setCommit(RevCommit commit) {
		this.commit = commit;
		return this;
	}

	public Revision setBlob(ObjectId blob) {
		this.blob = blob;
		return this;
	}

	public ObjectId getBlob() {
		return this.blob;
	}

	public Revision setNumber(int number) {
		this.number = number;
		return this;
	}

	public int getNumber() {
		return this.number;
	}

	public Revision addLine(Line line) {
		if (line != null) {
			line.setEnd(number);
			lines[index] = line.setNumber(index);
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
		Line line = getLine(lineNumber);
		if (line == null)
			return null;
		ObjectLoader loader = repository.open(blob
		byte[] contents;
		if (loader.isLarge())
			contents = IO.readWholeStream(loader.openStream()
					(int) loader.getSize()).array();
		else
			contents = loader.getCachedBytes();
		return new RawText(contents).getString(lineNumber);
	}

}
