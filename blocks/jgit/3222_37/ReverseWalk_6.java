
package org.eclipse.jgit.blame;

class Region {
	Region next;

	int resultStart;

	int sourceStart;

	int length;

	Region(int rs
		resultStart = rs;
		sourceStart = ss;
		length = len;
	}

	Region copy(int newSource) {
		return new Region(resultStart
	}

	Region splitFirst(int newSource
		return new Region(resultStart
	}

	void slideAndShrink(int d) {
		resultStart += d;
		sourceStart += d;
		length -= d;
	}

	Region deepCopy() {
		Region head = new Region(resultStart
		Region tail = head;
		for (Region n = next; n != null; n = n.next) {
			Region q = new Region(n.resultStart
			tail.next = q;
			tail = q;
		}
		return head;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		Region r = this;
		do {
			if (r != this)
				buf.append('
			buf.append(r.resultStart);
			buf.append('-');
			buf.append(r.resultStart + r.length);
			r = r.next;
		} while (r != null);
		return buf.toString();
	}
}
