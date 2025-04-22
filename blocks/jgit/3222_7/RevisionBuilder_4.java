package org.eclipse.jgit.blame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class Revision implements Iterable<Line> {

	private String path;

	private int number;

	private RevCommit commit;

	private ObjectId blob;

	private List<Line> lines;

	public Revision(String path) {
		this.path = path;
		this.lines = new ArrayList<Line>();
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		else if (obj instanceof Revision) {
			Revision other = (Revision) obj;
			return this.number == other.number && this.path.equals(other.path);
		} else
			return false;
	}

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
		if (line != null)
			lines.add(line.setNumber(lines.size()));
		return this;
	}

	public int getLineCount() {
		return this.lines.size();
	}

	public Line getLine(int lineNumber) {
		return lineNumber >= 0 && lineNumber < this.lines.size() ? this.lines
				.get(lineNumber) : null;
	}

	public Revision merge(Line line) {
		line.setEnd(this.number);
		this.lines.set(line.getNumber()
		return this;
	}

	public List<Line> getLines() {
		return this.lines;
	}

	public Iterator<Line> iterator() {
		return this.lines.iterator();
	}

	public String getLineContent(Repository repository
			throws IOException {
		Line line = getLine(lineNumber);
		if (line == null)
			return null;
		ObjectLoader loader = repository.open(getBlob()
		RawText text = new RawText(loader.getCachedBytes());
		return text.getString(lineNumber);
	}

}
