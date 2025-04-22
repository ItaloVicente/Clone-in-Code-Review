
package org.eclipse.jgit.patch;

import static org.eclipse.jgit.util.RawParseUtils.nextLF;
import static org.eclipse.jgit.util.RawParseUtils.parseBase10;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.util.MutableInteger;

public class CombinedHunkHeader extends HunkHeader {
	private static abstract class CombinedOldImage extends OldImage {
		int nContext;
	}

	private CombinedOldImage[] old;

	CombinedHunkHeader(CombinedFileHeader fh
		super(fh
		old = new CombinedOldImage[fh.getParentCount()];
		for (int i = 0; i < old.length; i++) {
			final int imagePos = i;
			old[i] = new CombinedOldImage() {
				@Override
				public AbbreviatedObjectId getId() {
					return fh.getOldId(imagePos);
				}
			};
		}
	}

	@Override
	public CombinedFileHeader getFileHeader() {
		return (CombinedFileHeader) super.getFileHeader();
	}

	@Override
	public OldImage getOldImage() {
		return getOldImage(0);
	}

	public OldImage getOldImage(int nthParent) {
		return old[nthParent];
	}

	@Override
	void parseHeader() {
		final byte[] buf = file.buf;
		final MutableInteger ptr = new MutableInteger();
		ptr.value = nextLF(buf

            for (CombinedOldImage old1 : old) {
                old1.startLine = -parseBase10(buf
                if (buf[ptr.value] == '
                    old1.lineCount = parseBase10(buf
                } else {
                    old1.lineCount = 1;
                }
            }

		newStartLine = parseBase10(buf
		if (buf[ptr.value] == '
			newLineCount = parseBase10(buf
		else
			newLineCount = 1;
	}

	@Override
	int parseBody(Patch script
		final byte[] buf = file.buf;
		int c = nextLF(buf

		for (CombinedOldImage o : old) {
			o.nDeleted = 0;
			o.nAdded = 0;
			o.nContext = 0;
		}
		nContext = 0;
		int nAdded = 0;

		SCAN: for (int eol; c < end; c = eol) {
			eol = nextLF(buf

			if (eol - c < old.length + 1) {
				break SCAN;
			}

			switch (buf[c]) {
			case ' ':
			case '-':
			case '+':
				break;

			default:
				break SCAN;
			}

			int localcontext = 0;
			for (int ancestor = 0; ancestor < old.length; ancestor++) {
				switch (buf[c + ancestor]) {
				case ' ':
					localcontext++;
					old[ancestor].nContext++;
					continue;

				case '-':
					old[ancestor].nDeleted++;
					continue;

				case '+':
					old[ancestor].nAdded++;
					nAdded++;
					continue;

				default:
					break SCAN;
				}
			}
			if (localcontext == old.length)
				nContext++;
		}

		for (int ancestor = 0; ancestor < old.length; ancestor++) {
			final CombinedOldImage o = old[ancestor];
			final int cmp = o.nContext + o.nDeleted;
			if (cmp < o.lineCount) {
				final int missingCnt = o.lineCount - cmp;
				script.error(buf
						JGitText.get().truncatedHunkLinesMissingForAncestor
						Integer.valueOf(missingCnt)
						Integer.valueOf(ancestor + 1)));
			}
		}

		if (nContext + nAdded < newLineCount) {
			final int missingCount = newLineCount - (nContext + nAdded);
			script.error(buf
					JGitText.get().truncatedHunkNewLinesMissing
					Integer.valueOf(missingCount)));
		}

		return c;
	}

	@Override
	void extractFileLines(OutputStream[] out) throws IOException {
		final byte[] buf = file.buf;
		int ptr = startOffset;
		int eol = nextLF(buf
		if (endOffset <= eol)
			return;

		out[0].write(buf

		SCAN: for (ptr = eol; ptr < endOffset; ptr = eol) {
			eol = nextLF(buf

			if (eol - ptr < old.length + 1) {
				break SCAN;
			}

			switch (buf[ptr]) {
			case ' ':
			case '-':
			case '+':
				break;

			default:
				break SCAN;
			}

			int delcnt = 0;
			for (int ancestor = 0; ancestor < old.length; ancestor++) {
				switch (buf[ptr + ancestor]) {
				case '-':
					delcnt++;
					out[ancestor].write(buf
					continue;

				case ' ':
					out[ancestor].write(buf
					continue;

				case '+':
					continue;

				default:
					break SCAN;
				}
			}
			if (delcnt < old.length) {
				out[old.length].write(buf
			}
		}
	}

	@Override
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

			if (eol - ptr < old.length + 1) {
				break SCAN;
			}

			switch (buf[ptr]) {
			case ' ':
			case '-':
			case '+':
				break;

			default:
				break SCAN;
			}

			boolean copied = false;
			for (int ancestor = 0; ancestor < old.length; ancestor++) {
				switch (buf[ptr + ancestor]) {
				case ' ':
				case '-':
					if (copied)
						skipLine(text
					else {
						copyLine(sb
						copied = true;
					}
					continue;

				case '+':
					continue;

				default:
					break SCAN;
				}
			}
			if (!copied) {
				copyLine(sb
			}
		}
	}
}
