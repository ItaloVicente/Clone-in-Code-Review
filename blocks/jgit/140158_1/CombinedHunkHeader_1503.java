
package org.eclipse.jgit.patch;

import static org.eclipse.jgit.lib.Constants.encodeASCII;
import static org.eclipse.jgit.util.RawParseUtils.match;
import static org.eclipse.jgit.util.RawParseUtils.nextLF;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.FileMode;

public class CombinedFileHeader extends FileHeader {

	private AbbreviatedObjectId[] oldIds;

	private FileMode[] oldModes;

	CombinedFileHeader(byte[] b
		super(b
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<? extends CombinedHunkHeader> getHunks() {
		return (List<CombinedHunkHeader>) super.getHunks();
	}

	@Override
	public int getParentCount() {
		return oldIds.length;
	}

	@Override
	public FileMode getOldMode() {
		return getOldMode(0);
	}

	public FileMode getOldMode(int nthParent) {
		return oldModes[nthParent];
	}

	@Override
	public AbbreviatedObjectId getOldId() {
		return getOldId(0);
	}

	public AbbreviatedObjectId getOldId(int nthParent) {
		return oldIds[nthParent];
	}

	@Override
	public String getScriptText(Charset ocs
		final Charset[] cs = new Charset[getParentCount() + 1];
		Arrays.fill(cs
		cs[getParentCount()] = ncs;
		return getScriptText(cs);
	}

	@Override
	public String getScriptText(Charset[] charsetGuess) {
		return super.getScriptText(charsetGuess);
	}

	@Override
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
				parseIndexLine(ptr + INDEX.length

			} else if (match(buf
				parseModeLine(ptr + MODE.length

			} else if (match(buf
				parseNewFileMode(ptr

			} else if (match(buf
				parseDeletedFileMode(ptr + DELETED_FILE_MODE.length

			} else {
				break;
			}

			ptr = eol;
		}
		return ptr;
	}

	@Override
	protected void parseIndexLine(int ptr
		final List<AbbreviatedObjectId> ids = new ArrayList<>();
		while (ptr < eol) {
			final int comma = nextLF(buf
			if (eol <= comma)
				break;
			ids.add(AbbreviatedObjectId.fromString(buf
			ptr = comma;
		}

		oldIds = new AbbreviatedObjectId[ids.size() + 1];
		ids.toArray(oldIds);
		final int dot2 = nextLF(buf
		oldIds[ids.size()] = AbbreviatedObjectId.fromString(buf
		newId = AbbreviatedObjectId.fromString(buf
		oldModes = new FileMode[oldIds.length];
	}

	@Override
	protected void parseNewFileMode(int ptr
		for (int i = 0; i < oldModes.length; i++)
			oldModes[i] = FileMode.MISSING;
		super.parseNewFileMode(ptr
	}

	@Override
	HunkHeader newHunkHeader(int offset) {
		return new CombinedHunkHeader(this
	}

	private void parseModeLine(int ptr
		int n = 0;
		while (ptr < eol) {
			final int comma = nextLF(buf
			if (eol <= comma)
				break;
			oldModes[n++] = parseFileMode(ptr
			ptr = comma;
		}
		final int dot2 = nextLF(buf
		oldModes[n] = parseFileMode(ptr
		newMode = parseFileMode(dot2 + 1
	}

	private void parseDeletedFileMode(int ptr
		changeType = ChangeType.DELETE;
		int n = 0;
		while (ptr < eol) {
			final int comma = nextLF(buf
			if (eol <= comma)
				break;
			oldModes[n++] = parseFileMode(ptr
			ptr = comma;
		}
		oldModes[n] = parseFileMode(ptr
		newMode = FileMode.MISSING;
	}
}
