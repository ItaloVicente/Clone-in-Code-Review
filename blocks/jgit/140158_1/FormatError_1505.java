
package org.eclipse.jgit.patch;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.encodeASCII;
import static org.eclipse.jgit.util.RawParseUtils.decode;
import static org.eclipse.jgit.util.RawParseUtils.decodeNoFallback;
import static org.eclipse.jgit.util.RawParseUtils.extractBinaryString;
import static org.eclipse.jgit.util.RawParseUtils.match;
import static org.eclipse.jgit.util.RawParseUtils.nextLF;
import static org.eclipse.jgit.util.RawParseUtils.parseBase10;

import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.util.QuotedString;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.TemporaryBuffer;

public class FileHeader extends DiffEntry {















	public static enum PatchType {
		UNIFIED

		BINARY

		GIT_BINARY;
	}

	final byte[] buf;

	final int startOffset;

	int endOffset;

	PatchType patchType;

	private List<HunkHeader> hunks;

	BinaryHunk forwardBinaryHunk;

	BinaryHunk reverseBinaryHunk;

	public FileHeader(byte[] headerLines
		this(headerLines
		endOffset = headerLines.length;
		int ptr = parseGitFileName(Patch.DIFF_GIT.length
		parseGitHeaders(ptr
		this.patchType = type;
		addHunk(new HunkHeader(this
	}

	FileHeader(byte[] b
		buf = b;
		startOffset = offset;
		patchType = PatchType.UNIFIED;
	}

	int getParentCount() {
		return 1;
	}

	public byte[] getBuffer() {
		return buf;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public String getScriptText() {
		return getScriptText(null
	}

	public String getScriptText(Charset oldCharset
		return getScriptText(new Charset[] { oldCharset
	}

	String getScriptText(Charset[] charsetGuess) {
		if (getHunks().isEmpty()) {
			return extractBinaryString(buf
		}

		if (charsetGuess != null && charsetGuess.length != getParentCount() + 1)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().expectedCharacterEncodingGuesses
					Integer.valueOf(getParentCount() + 1)));

		if (trySimpleConversion(charsetGuess)) {
			Charset cs = charsetGuess != null ? charsetGuess[0] : null;
			if (cs == null) {
				cs = UTF_8;
			}
			try {
				return decodeNoFallback(cs
			} catch (CharacterCodingException cee) {
			}
		}

		final StringBuilder r = new StringBuilder(endOffset - startOffset);

		final int hdrEnd = getHunks().get(0).getStartOffset();
		for (int ptr = startOffset; ptr < hdrEnd;) {
			final int eol = Math.min(hdrEnd
			r.append(extractBinaryString(buf
			ptr = eol;
		}

		final String[] files = extractFileLines(charsetGuess);
		final int[] offsets = new int[files.length];
		for (HunkHeader h : getHunks())
			h.extractFileLines(r
		return r.toString();
	}

	private static boolean trySimpleConversion(Charset[] charsetGuess) {
		if (charsetGuess == null)
			return true;
		for (int i = 1; i < charsetGuess.length; i++) {
			if (charsetGuess[i] != charsetGuess[0])
				return false;
		}
		return true;
	}

	private String[] extractFileLines(Charset[] csGuess) {
		final TemporaryBuffer[] tmp = new TemporaryBuffer[getParentCount() + 1];
		try {
			for (int i = 0; i < tmp.length; i++)
				tmp[i] = new TemporaryBuffer.Heap(Integer.MAX_VALUE);
			for (HunkHeader h : getHunks())
				h.extractFileLines(tmp);

			final String[] r = new String[tmp.length];
			for (int i = 0; i < tmp.length; i++) {
				Charset cs = csGuess != null ? csGuess[i] : null;
				if (cs == null) {
					cs = UTF_8;
				}
				r[i] = RawParseUtils.decode(cs
			}
			return r;
		} catch (IOException ioe) {
			throw new RuntimeException(JGitText.get().cannotConvertScriptToText
		}
	}

	public PatchType getPatchType() {
		return patchType;
	}

	public boolean hasMetaDataChanges() {
		return changeType != ChangeType.MODIFY || newMode != oldMode;
	}

	public List<? extends HunkHeader> getHunks() {
		if (hunks == null)
			return Collections.emptyList();
		return hunks;
	}

	void addHunk(HunkHeader h) {
		if (h.getFileHeader() != this)
			throw new IllegalArgumentException(JGitText.get().hunkBelongsToAnotherFile);
		if (hunks == null)
			hunks = new ArrayList<>();
		hunks.add(h);
	}

	HunkHeader newHunkHeader(int offset) {
		return new HunkHeader(this
	}

	public BinaryHunk getForwardBinaryHunk() {
		return forwardBinaryHunk;
	}

	public BinaryHunk getReverseBinaryHunk() {
		return reverseBinaryHunk;
	}

	public EditList toEditList() {
		final EditList r = new EditList();
		for (HunkHeader hunk : hunks)
			r.addAll(hunk.toEditList());
		return r;
	}

	int parseGitFileName(int ptr
		final int eol = nextLF(buf
		final int bol = ptr;
		if (eol >= end) {
			return -1;
		}


		final int aStart = nextLF(buf
		if (aStart >= eol)
			return eol;

		while (ptr < eol) {
			final int sp = nextLF(buf
			if (sp >= eol) {
				return eol;
			}
			final int bStart = nextLF(buf
			if (bStart >= eol)
				return eol;

			if (eq(aStart
				if (buf[bol] == '"') {
					if (buf[sp - 2] != '"') {
						return eol;
					}
					oldPath = QuotedString.GIT_PATH.dequote(buf
					oldPath = p1(oldPath);
				} else {
					oldPath = decode(UTF_8
				}
				newPath = oldPath;
				return eol;
			}

			ptr = sp;
		}

		return eol;
	}

	int parseGitHeaders(int ptr
		while (ptr < end) {
			final int eol = nextLF(buf
			if (isHunkHdr(buf
				break;

			} else if (match(buf
				parseOldName(ptr

			} else if (match(buf
				parseNewName(ptr

			} else if (match(buf
				oldMode = parseFileMode(ptr + OLD_MODE.length

			} else if (match(buf
				newMode = parseFileMode(ptr + NEW_MODE.length

			} else if (match(buf
				oldMode = parseFileMode(ptr + DELETED_FILE_MODE.length
				newMode = FileMode.MISSING;
				changeType = ChangeType.DELETE;

			} else if (match(buf
				parseNewFileMode(ptr

			} else if (match(buf
				oldPath = parseName(oldPath
				changeType = ChangeType.COPY;

			} else if (match(buf
				newPath = parseName(newPath
				changeType = ChangeType.COPY;

			} else if (match(buf
				oldPath = parseName(oldPath
				changeType = ChangeType.RENAME;

			} else if (match(buf
				newPath = parseName(newPath
				changeType = ChangeType.RENAME;

			} else if (match(buf
				oldPath = parseName(oldPath
				changeType = ChangeType.RENAME;

			} else if (match(buf
				newPath = parseName(newPath
				changeType = ChangeType.RENAME;

			} else if (match(buf
				score = parseBase10(buf

			} else if (match(buf
				score = parseBase10(buf

			} else if (match(buf
				parseIndexLine(ptr + INDEX.length

			} else {
				break;
			}

			ptr = eol;
		}
		return ptr;
	}

	void parseOldName(int ptr
		oldPath = p1(parseName(oldPath
		if (oldPath == DEV_NULL)
			changeType = ChangeType.ADD;
	}

	void parseNewName(int ptr
		newPath = p1(parseName(newPath
		if (newPath == DEV_NULL)
			changeType = ChangeType.DELETE;
	}

	void parseNewFileMode(int ptr
		oldMode = FileMode.MISSING;
		newMode = parseFileMode(ptr + NEW_FILE_MODE.length
		changeType = ChangeType.ADD;
	}

	int parseTraditionalHeaders(int ptr
		while (ptr < end) {
			final int eol = nextLF(buf
			if (isHunkHdr(buf
				break;

			} else if (match(buf
				parseOldName(ptr

			} else if (match(buf
				parseNewName(ptr

			} else {
				break;
			}

			ptr = eol;
		}
		return ptr;
	}

	private String parseName(String expect
		if (ptr == end)
			return expect;

		String r;
		if (buf[ptr] == '"') {
			r = QuotedString.GIT_PATH.dequote(buf
		} else {
			int tab = end;
			while (ptr < tab && buf[tab - 1] != '\t')
				tab--;
			if (ptr == tab)
				tab = end;
			r = decode(UTF_8
		}

		if (r.equals(DEV_NULL))
			r = DEV_NULL;
		return r;
	}

	private static String p1(final String r) {
		final int s = r.indexOf('/');
		return s > 0 ? r.substring(s + 1) : r;
	}

	FileMode parseFileMode(int ptr
		int tmp = 0;
		while (ptr < end - 1) {
			tmp <<= 3;
			tmp += buf[ptr++] - '0';
		}
		return FileMode.fromBits(tmp);
	}

	void parseIndexLine(int ptr
		final int dot2 = nextLF(buf
		final int mode = nextLF(buf

		oldId = AbbreviatedObjectId.fromString(buf
		newId = AbbreviatedObjectId.fromString(buf

		if (mode < end)
			newMode = oldMode = parseFileMode(mode
	}

	private boolean eq(int aPtr
		if (aEnd - aPtr != bEnd - bPtr) {
			return false;
		}
		while (aPtr < aEnd) {
			if (buf[aPtr++] != buf[bPtr++])
				return false;
		}
		return true;
	}

	static int isHunkHdr(byte[] buf
		int ptr = start;
		while (ptr < end && buf[ptr] == '@')
			ptr++;
		if (ptr - start < 2)
			return 0;
		if (ptr == end || buf[ptr++] != ' ')
			return 0;
		if (ptr == end || buf[ptr++] != '-')
			return 0;
		return (ptr - 3) - start;
	}
}
