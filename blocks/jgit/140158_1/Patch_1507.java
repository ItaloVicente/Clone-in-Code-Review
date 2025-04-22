
package org.eclipse.jgit.patch;

import static org.eclipse.jgit.util.RawParseUtils.match;
import static org.eclipse.jgit.util.RawParseUtils.nextLF;
import static org.eclipse.jgit.util.RawParseUtils.parseBase10;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.util.MutableInteger;

public class HunkHeader {
	public abstract static class OldImage {
		int startLine;

		int lineCount;

		int nDeleted;

		int nAdded;

		public int getStartLine() {
			return startLine;
		}

		public int getLineCount() {
			return lineCount;
		}

		public int getLinesDeleted() {
			return nDeleted;
		}

		public int getLinesAdded() {
			return nAdded;
		}

		public abstract AbbreviatedObjectId getId();
	}

	final FileHeader file;

	final int startOffset;

	int endOffset;

	private final OldImage old;

	int newStartLine;

	int newLineCount;

	int nContext;

	private EditList editList;

	HunkHeader(FileHeader fh
		this(fh
			@Override
			public AbbreviatedObjectId getId() {
				return fh.getOldId();
			}
		});
	}

	HunkHeader(FileHeader fh
		file = fh;
		startOffset = offset;
		old = oi;
	}

	HunkHeader(FileHeader fh
		this(fh
		this.editList = editList;
		endOffset = startOffset;
		nContext = 0;
		if (editList.isEmpty()) {
			newStartLine = 0;
			newLineCount = 0;
		} else {
			newStartLine = editList.get(0).getBeginB();
			Edit last = editList.get(editList.size() - 1);
			newLineCount = last.getEndB() - newStartLine;
		}
	}

	public FileHeader getFileHeader() {
		return file;
	}

	public byte[] getBuffer() {
		return file.buf;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public OldImage getOldImage() {
		return old;
	}

	public int getNewStartLine() {
		return newStartLine;
	}

	public int getNewLineCount() {
		return newLineCount;
	}

	public int getLinesContext() {
		return nContext;
	}

	public EditList toEditList() {
		if (editList == null) {
			editList = new EditList();
			final byte[] buf = file.buf;
			int c = nextLF(buf
			int oLine = old.startLine;
			int nLine = newStartLine;
			Edit in = null;

			SCAN: for (; c < endOffset; c = nextLF(buf
				switch (buf[c]) {
				case ' ':
				case '\n':
					in = null;
					oLine++;
					nLine++;
					continue;

				case '-':
					if (in == null) {
						in = new Edit(oLine - 1
						editList.add(in);
					}
					oLine++;
					in.extendA();
					continue;

				case '+':
					if (in == null) {
						in = new Edit(oLine - 1
						editList.add(in);
					}
					nLine++;
					in.extendB();
					continue;

					continue;

				default:
					break SCAN;
				}
			}
		}
		return editList;
	}

	void parseHeader() {
		final byte[] buf = file.buf;
		final MutableInteger ptr = new MutableInteger();
		ptr.value = nextLF(buf
		old.startLine = -parseBase10(buf
		if (buf[ptr.value] == '
			old.lineCount = parseBase10(buf
		else
			old.lineCount = 1;

		newStartLine = parseBase10(buf
		if (buf[ptr.value] == '
			newLineCount = parseBase10(buf
		else
			newLineCount = 1;
	}

	int parseBody(Patch script
		final byte[] buf = file.buf;
		int c = nextLF(buf

		old.nDeleted = 0;
		old.nAdded = 0;

		SCAN: for (; c < end; last = c
			switch (buf[c]) {
			case ' ':
			case '\n':
				nContext++;
				continue;

			case '-':
				old.nDeleted++;
				continue;

			case '+':
				old.nAdded++;
				continue;

				continue;

			default:
				break SCAN;
			}
		}

		if (last < end && nContext + old.nDeleted - 1 == old.lineCount
				&& nContext + old.nAdded == newLineCount
				&& match(buf
			old.nDeleted--;
			return last;
		}

		if (nContext + old.nDeleted < old.lineCount) {
			final int missingCount = old.lineCount - (nContext + old.nDeleted);
			script.error(buf
					JGitText.get().truncatedHunkOldLinesMissing
					Integer.valueOf(missingCount)));

		} else if (nContext + old.nAdded < newLineCount) {
			final int missingCount = newLineCount - (nContext + old.nAdded);
			script.error(buf
					JGitText.get().truncatedHunkNewLinesMissing
					Integer.valueOf(missingCount)));

		} else if (nContext + old.nDeleted > old.lineCount
				|| nContext + old.nAdded > newLineCount) {
					+ (nContext + old.nAdded);
			script.warn(buf
					JGitText.get().hunkHeaderDoesNotMatchBodyLineCountOf
		}

		return c;
	}

	void extractFileLines(OutputStream[] out) throws IOException {
		final byte[] buf = file.buf;
		int ptr = startOffset;
		int eol = nextLF(buf
		if (endOffset <= eol)
			return;

		out[0].write(buf

		SCAN: for (ptr = eol; ptr < endOffset; ptr = eol) {
			eol = nextLF(buf
			switch (buf[ptr]) {
			case ' ':
			case '\n':
			case '\\':
				out[0].write(buf
				out[1].write(buf
				break;
			case '-':
				out[0].write(buf
				break;
			case '+':
				out[1].write(buf
				break;
			default:
				break SCAN;
			}
		}
	}

	void extractFileLines(final StringBuilder sb
			final int[] offsets) {
		final byte[] buf = file.buf;
		int ptr = startOffset;
		int eol = nextLF(buf
		if (endOffset <= eol)
			return;
		copyLine(sb
		SCAN: for (ptr = eol; ptr < endOffset; ptr = eol) {
			eol = nextLF(buf
			switch (buf[ptr]) {
			case ' ':
			case '\n':
			case '\\':
				copyLine(sb
				skipLine(text
				break;
			case '-':
				copyLine(sb
				break;
			case '+':
				copyLine(sb
				break;
			default:
				break SCAN;
			}
		}
	}

	void copyLine(final StringBuilder sb
			final int[] offsets
		final String s = text[fileIdx];
		final int start = offsets[fileIdx];
		int end = s.indexOf('\n'
		if (end < 0)
			end = s.length();
		else
			end++;
		sb.append(s
		offsets[fileIdx] = end;
	}

	void skipLine(final String[] text
			final int fileIdx) {
		final String s = text[fileIdx];
		final int end = s.indexOf('\n'
		offsets[fileIdx] = end < 0 ? s.length() : end + 1;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("HunkHeader[");
		buf.append(getOldImage().getStartLine());
		buf.append('
		buf.append(getOldImage().getLineCount());
		buf.append("->");
		buf.append(getNewStartLine()).append('
		buf.append(']');
		return buf.toString();
	}
}
