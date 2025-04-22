
package org.eclipse.jgit.diff;

import java.util.LinkedList;
import java.util.List;

public class ExpandableContent {
	class ContentChunk {
		byte[] content;

		public ContentChunk(byte[] content
			this.content = content;
			this.from = from;
			this.to = to;
		}
	}

	private List<ContentChunk> chunks = new LinkedList<ContentChunk>();

	public void add(RawText text

		int from = text.lines.get(startLine + 1);
		int to = text.lines.get(endLine + 1);
		chunks.add(new ContentChunk(text.content
	}

	public void add(RawText text) {
		add(text
	}

	public String toString() {
		StringBuilder ret = new StringBuilder();
		for (ContentChunk c : chunks) {
			ret.append(new String(c.content
		}
		return (ret.toString());
	}
}
