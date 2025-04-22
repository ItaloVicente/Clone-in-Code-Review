package org.eclipse.jgit.lines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

public class Revision implements Iterable<Line> {

	private int number;

	private int size;

	private RevCommit commit;

	private ObjectId blob;

	private List<Line> lines;

	public Revision() {
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
			return this.number == other.number
					&& this.lines.equals(other.lines);
		} else
			return false;
	}

	public String toString() {
		return "Revision: " + this.number + " Commit: " + this.commit.name()
				+ " Blob: " + this.blob.name();
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
			lines.add(line.setNumber(lines.size()));
			this.size += line.getContent().length();
		}
		return this;
	}

	public int getLineCount() {
		return this.lines.size();
	}

	public Line getLine(int lineNumber) {
		return lineNumber >= 0 && lineNumber < this.lines.size() ? this.lines
				.get(lineNumber) : null;
	}

	public Revision merge(Line line
		line.setEnd(this.number);
		this.size += line.getContent().length();
		Line removed = this.lines.set(lineNumber
		if (removed != null)
			this.size -= removed.getContent().length();
		return this;
	}

	public Revision merge(Line line) {
		return merge(line
	}

	public List<Line> getLines() {
		return this.lines;
	}

	public Iterator<Line> iterator() {
		return this.lines.iterator();
	}

	public byte[] getBytes() {
		byte[] bytes = new byte[this.size + getLineCount()];
		int index = 0;
		for (Line line : this) {
			byte[] copy = line.getContent().getBytes();
			System.arraycopy(copy
			index += copy.length;
			bytes[index] = '\n';
			index++;
		}
		return bytes;
	}
}
