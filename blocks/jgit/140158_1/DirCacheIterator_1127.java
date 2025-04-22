
package org.eclipse.jgit.dircache;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Arrays;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.MutableInteger;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.SystemReader;

public class DirCacheEntry {
	private static final byte[] nullpad = new byte[8];

	public static final int STAGE_0 = 0;

	public static final int STAGE_1 = 1;

	public static final int STAGE_2 = 2;

	public static final int STAGE_3 = 3;

	private static final int P_CTIME = 0;


	private static final int P_MTIME = 8;




	private static final int P_MODE = 24;



	private static final int P_SIZE = 36;

	private static final int P_OBJECTID = 40;

	private static final int P_FLAGS = 60;
	private static final int P_FLAGS2 = 62;

	private static final int NAME_MASK = 0xfff;

	private static final int INTENT_TO_ADD = 0x20000000;
	private static final int SKIP_WORKTREE = 0x40000000;
	private static final int EXTENDED_FLAGS = (INTENT_TO_ADD | SKIP_WORKTREE);

	private static final int INFO_LEN = 62;
	private static final int INFO_LEN_EXTENDED = 64;

	private static final int EXTENDED = 0x40;
	private static final int ASSUME_VALID = 0x80;

	private static final int UPDATE_NEEDED = 0x1;

	private final byte[] info;

	private final int infoOffset;

	final byte[] path;

	private byte inCoreFlags;

	DirCacheEntry(final byte[] sharedInfo
			final InputStream in
			final int smudge_ns) throws IOException {
		info = sharedInfo;
		infoOffset = infoAt.value;

		IO.readFully(in

		final int len;
		if (isExtended()) {
			len = INFO_LEN_EXTENDED;
			IO.readFully(in

			if ((getExtendedFlags() & ~EXTENDED_FLAGS) != 0)
				throw new IOException(MessageFormat.format(JGitText.get()
						.DIRCUnrecognizedExtendedFlags
		} else
			len = INFO_LEN;

		infoAt.value += len;
		md.update(info

		int pathLen = NB.decodeUInt16(info
		int skipped = 0;
		if (pathLen < NAME_MASK) {
			path = new byte[pathLen];
			IO.readFully(in
			md.update(path
		} else {
			final ByteArrayOutputStream tmp = new ByteArrayOutputStream();
			{
				final byte[] buf = new byte[NAME_MASK];
				IO.readFully(in
				tmp.write(buf);
			}
			for (;;) {
				final int c = in.read();
				if (c < 0)
					throw new EOFException(JGitText.get().shortReadOfBlock);
				if (c == 0)
					break;
				tmp.write(c);
			}
			path = tmp.toByteArray();
			pathLen = path.length;
			md.update(path
			md.update((byte) 0);
		}

		try {
			checkPath(path);
		} catch (InvalidPathException e) {
			CorruptObjectException p =
				new CorruptObjectException(e.getMessage());
			if (e.getCause() != null)
				p.initCause(e.getCause());
			throw p;
		}

		final int actLen = len + pathLen;
		final int expLen = (actLen + 8) & ~7;
		final int padLen = expLen - actLen - skipped;
		if (padLen > 0) {
			IO.skipFully(in
			md.update(nullpad
		}

		if (mightBeRacilyClean(smudge_s
			smudgeRacilyClean();
	}

	public DirCacheEntry(String newPath) {
		this(Constants.encode(newPath)
	}

	public DirCacheEntry(String newPath
		this(Constants.encode(newPath)
	}

	public DirCacheEntry(byte[] newPath) {
		this(newPath
	}

	@SuppressWarnings("boxing")
	public DirCacheEntry(byte[] path
		checkPath(path);
		if (stage < 0 || 3 < stage)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidStageForPath
					stage

		info = new byte[INFO_LEN];
		infoOffset = 0;
		this.path = path;

		int flags = ((stage & 0x3) << 12);
		if (path.length < NAME_MASK)
			flags |= path.length;
		else
			flags |= NAME_MASK;
		NB.encodeInt16(info
	}

	public DirCacheEntry(DirCacheEntry src) {
		path = src.path;
		info = new byte[INFO_LEN];
		infoOffset = 0;
		System.arraycopy(src.info
	}

	void write(OutputStream os) throws IOException {
		final int len = isExtended() ? INFO_LEN_EXTENDED : INFO_LEN;
		final int pathLen = path.length;
		os.write(info
		os.write(path

		final int actLen = len + pathLen;
		final int expLen = (actLen + 8) & ~7;
		if (actLen != expLen)
			os.write(nullpad
	}

	public final boolean mightBeRacilyClean(int smudge_s
		final int base = infoOffset + P_MTIME;
		final int mtime = NB.decodeInt32(info
		if (smudge_s == mtime)
			return smudge_ns <= NB.decodeInt32(info
		return false;
	}

	public final void smudgeRacilyClean() {
		final int base = infoOffset + P_SIZE;
		Arrays.fill(info
	}

	public final boolean isSmudged() {
		final int base = infoOffset + P_OBJECTID;
		return (getLength() == 0) && (Constants.EMPTY_BLOB_ID.compareTo(info
	}

	final byte[] idBuffer() {
		return info;
	}

	final int idOffset() {
		return infoOffset + P_OBJECTID;
	}

	public boolean isAssumeValid() {
		return (info[infoOffset + P_FLAGS] & ASSUME_VALID) != 0;
	}

	public void setAssumeValid(boolean assume) {
		if (assume)
			info[infoOffset + P_FLAGS] |= ASSUME_VALID;
		else
			info[infoOffset + P_FLAGS] &= ~ASSUME_VALID;
	}

	public boolean isUpdateNeeded() {
		return (inCoreFlags & UPDATE_NEEDED) != 0;
	}

	public void setUpdateNeeded(boolean updateNeeded) {
		if (updateNeeded)
			inCoreFlags |= UPDATE_NEEDED;
		else
			inCoreFlags &= ~UPDATE_NEEDED;
	}

	public int getStage() {
		return (info[infoOffset + P_FLAGS] >>> 4) & 0x3;
	}

	public boolean isSkipWorkTree() {
		return (getExtendedFlags() & SKIP_WORKTREE) != 0;
	}

	public boolean isIntentToAdd() {
		return (getExtendedFlags() & INTENT_TO_ADD) != 0;
	}

	public boolean isMerged() {
		return getStage() == STAGE_0;
	}

	public int getRawMode() {
		return NB.decodeInt32(info
	}

	public FileMode getFileMode() {
		return FileMode.fromBits(getRawMode());
	}

	public void setFileMode(FileMode mode) {
		switch (mode.getBits() & FileMode.TYPE_MASK) {
		case FileMode.TYPE_MISSING:
		case FileMode.TYPE_TREE:
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidModeForPath
		}
		NB.encodeInt32(info
	}

	void setFileMode(int mode) {
		NB.encodeInt32(info
	}

	public long getCreationTime() {
		return decodeTS(P_CTIME);
	}

	public void setCreationTime(long when) {
		encodeTS(P_CTIME
	}

	public long getLastModified() {
		return decodeTS(P_MTIME);
	}

	public void setLastModified(long when) {
		encodeTS(P_MTIME
	}

	public int getLength() {
		return NB.decodeInt32(info
	}

	public void setLength(int sz) {
		NB.encodeInt32(info
	}

	public void setLength(long sz) {
		setLength((int) sz);
	}

	public ObjectId getObjectId() {
		return ObjectId.fromRaw(idBuffer()
	}

	public void setObjectId(AnyObjectId id) {
		id.copyRawTo(idBuffer()
	}

	public void setObjectIdFromRaw(byte[] bs
		final int n = Constants.OBJECT_ID_LENGTH;
		System.arraycopy(bs
	}

	public String getPathString() {
		return toString(path);
	}

	public byte[] getRawPath() {
		return path.clone();
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return getFileMode() + " " + getLength() + " " + getLastModified()
				+ " " + getObjectId() + " " + getStage() + " "
				+ getPathString() + "\n";
	}

	public void copyMetaData(DirCacheEntry src) {
		copyMetaData(src
	}

	void copyMetaData(DirCacheEntry src
		int origflags = NB.decodeUInt16(info
		int newflags = NB.decodeUInt16(src.info
		System.arraycopy(src.info
		final int pLen = origflags & NAME_MASK;
		final int SHIFTED_STAGE_MASK = 0x3 << 12;
		final int pStageShifted;
		if (keepStage)
			pStageShifted = origflags & SHIFTED_STAGE_MASK;
		else
			pStageShifted = newflags & SHIFTED_STAGE_MASK;
		NB.encodeInt16(info
				| (newflags & ~NAME_MASK & ~SHIFTED_STAGE_MASK));
	}

	boolean isExtended() {
		return (info[infoOffset + P_FLAGS] & EXTENDED) != 0;
	}

	private long decodeTS(int pIdx) {
		final int base = infoOffset + pIdx;
		final int sec = NB.decodeInt32(info
		final int ms = NB.decodeInt32(info
		return 1000L * sec + ms;
	}

	private void encodeTS(int pIdx
		final int base = infoOffset + pIdx;
		NB.encodeInt32(info
		NB.encodeInt32(info
	}

	private int getExtendedFlags() {
		if (isExtended())
			return NB.decodeUInt16(info
		else
			return 0;
	}

	private static void checkPath(byte[] path) {
		try {
			SystemReader.getInstance().checkPath(path);
		} catch (CorruptObjectException e) {
			InvalidPathException p = new InvalidPathException(toString(path));
			p.initCause(e);
			throw p;
		}
	}

	static String toString(byte[] path) {
		return UTF_8.decode(ByteBuffer.wrap(path)).toString();
	}

	static int getMaximumInfoLength(boolean extended) {
		return extended ? INFO_LEN_EXTENDED : INFO_LEN;
	}
}
