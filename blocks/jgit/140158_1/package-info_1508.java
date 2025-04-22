
package org.eclipse.jgit.patch;

import static org.eclipse.jgit.lib.Constants.encodeASCII;
import static org.eclipse.jgit.patch.FileHeader.NEW_NAME;
import static org.eclipse.jgit.patch.FileHeader.OLD_NAME;
import static org.eclipse.jgit.patch.FileHeader.isHunkHdr;
import static org.eclipse.jgit.util.RawParseUtils.match;
import static org.eclipse.jgit.util.RawParseUtils.nextLF;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.TemporaryBuffer;

public class Patch {



	private static final byte[][] BIN_HEADERS = new byte[][] {
			encodeASCII("Binary files ")




	private final List<FileHeader> files;

	private final List<FormatError> errors;

	public Patch() {
		files = new ArrayList<>();
		errors = new ArrayList<>(0);
	}

	public void addFile(FileHeader fh) {
		files.add(fh);
	}

	public List<? extends FileHeader> getFiles() {
		return files;
	}

	public void addError(FormatError err) {
		errors.add(err);
	}

	public List<FormatError> getErrors() {
		return errors;
	}

	public void parse(InputStream is) throws IOException {
		final byte[] buf = readFully(is);
		parse(buf
	}

	private static byte[] readFully(InputStream is) throws IOException {
		try (TemporaryBuffer b = new TemporaryBuffer.Heap(Integer.MAX_VALUE)) {
			b.copy(is);
			return b.toByteArray();
		}
	}

	public void parse(byte[] buf
		while (ptr < end)
			ptr = parseFile(buf
	}

	private int parseFile(byte[] buf
		while (c < end) {
			if (isHunkHdr(buf
				error(buf
				c = nextLF(buf
				continue;
			}

			if (match(buf
				return parseDiffGit(buf
			if (match(buf
				return parseDiffCombined(DIFF_CC
			if (match(buf
				return parseDiffCombined(DIFF_COMBINED

			final int n = nextLF(buf
			if (n >= end) {
				return end;
			}

			if (n - c < 6) {
				c = n;
				continue;
			}

			if (match(buf
				final int f = nextLF(buf
				if (f >= end)
					return end;
				if (isHunkHdr(buf
					return parseTraditionalPatch(buf
			}

			c = n;
		}
		return c;
	}

	private int parseDiffGit(byte[] buf
		final FileHeader fh = new FileHeader(buf
		int ptr = fh.parseGitFileName(start + DIFF_GIT.length
		if (ptr < 0)
			return skipFile(buf

		ptr = fh.parseGitHeaders(ptr
		ptr = parseHunks(fh
		fh.endOffset = ptr;
		addFile(fh);
		return ptr;
	}

	private int parseDiffCombined(final byte[] hdr
			final int start
		final CombinedFileHeader fh = new CombinedFileHeader(buf
		int ptr = fh.parseGitFileName(start + hdr.length
		if (ptr < 0)
			return skipFile(buf

		ptr = fh.parseGitHeaders(ptr
		ptr = parseHunks(fh
		fh.endOffset = ptr;
		addFile(fh);
		return ptr;
	}

	private int parseTraditionalPatch(final byte[] buf
			final int end) {
		final FileHeader fh = new FileHeader(buf
		int ptr = fh.parseTraditionalHeaders(start
		ptr = parseHunks(fh
		fh.endOffset = ptr;
		addFile(fh);
		return ptr;
	}

	private static int skipFile(byte[] buf
		ptr = nextLF(buf
		if (match(buf
			ptr = nextLF(buf
		return ptr;
	}

	private int parseHunks(FileHeader fh
		final byte[] buf = fh.buf;
		while (c < end) {
			if (match(buf
				break;
			if (match(buf
				break;
			if (match(buf
				break;
			if (match(buf
				break;
			if (match(buf
				break;

			if (isHunkHdr(buf
				final HunkHeader h = fh.newHunkHeader(c);
				h.parseHeader();
				c = h.parseBody(this
				h.endOffset = c;
				fh.addHunk(h);
				if (c < end) {
					switch (buf[c]) {
					case '@':
					case 'd':
					case '\n':
						break;
					default:
						if (match(buf
							warn(buf
					}
				}
				continue;
			}

			final int eol = nextLF(buf
			if (fh.getHunks().isEmpty() && match(buf
				fh.patchType = FileHeader.PatchType.GIT_BINARY;
				return parseGitBinary(fh
			}

			if (fh.getHunks().isEmpty() && BIN_TRAILER.length < eol - c
					&& match(buf
					&& matchAny(buf
				fh.patchType = FileHeader.PatchType.BINARY;
				return eol;
			}

			c = eol;
		}

		if (fh.getHunks().isEmpty()
				&& fh.getPatchType() == FileHeader.PatchType.UNIFIED
				&& !fh.hasMetaDataChanges()) {
			fh.patchType = FileHeader.PatchType.BINARY;
		}

		return c;
	}

	private int parseGitBinary(FileHeader fh
		final BinaryHunk postImage = new BinaryHunk(fh
		final int nEnd = postImage.parseHunk(c
		if (nEnd < 0) {
			error(fh.buf
			return c;
		}
		c = nEnd;
		postImage.endOffset = c;
		fh.forwardBinaryHunk = postImage;

		final BinaryHunk preImage = new BinaryHunk(fh
		final int oEnd = preImage.parseHunk(c
		if (oEnd >= 0) {
			c = oEnd;
			preImage.endOffset = c;
			fh.reverseBinaryHunk = preImage;
		}

		return c;
	}

	void warn(byte[] buf
		addError(new FormatError(buf
	}

	void error(byte[] buf
		addError(new FormatError(buf
	}

	private static boolean matchAny(final byte[] buf
			final byte[][] srcs) {
		for (byte[] s : srcs) {
			if (match(buf
				return true;
		}
		return false;
	}
}
