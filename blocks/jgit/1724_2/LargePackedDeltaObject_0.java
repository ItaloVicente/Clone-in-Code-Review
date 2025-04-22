
package org.eclipse.jgit.storage.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.zip.CRC32;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.io.SeekableInputStream;

class LargeDeltaBaseInputStream extends SeekableInputStream {
	private static final int HDR_SZ = 20;

	private static final byte[] MAGIC = { 'c'

	private final WindowCursor wc;

	private final PackFile packFile;

	private final long packOffset;

	private final long size;

	private long ptr;

	private int blockSize;

	private ObjectStream origIn;

	private SeekableInputStream seekIn;

	private CRC32 crc32;

	private byte[] blockBuffer;

	private long blockBufferId;

	private int blockBufferLen;

	private ObjectId baseId;

	private File basePath;

	LargeDeltaBaseInputStream(ObjectStream in
			WindowCursor wc
		this.origIn = in;
		this.packFile = pack;
		this.packOffset = ofs;
		this.wc = wc;
		this.size = origIn.getSize();
		this.blockSize = desiredBlockSize;
	}

	@Override
	public void seek(long offset) throws IOException {
		if (offset < ptr && seekIn == null) {

			origIn.close();
			origIn = null;

			crc32 = new CRC32();
			baseId = packFile.findObjectForOffset(packOffset);
			initSeekIn();
		}

		if (origIn != null)
			IO.skipFully(origIn
		ptr = offset;
	}

	@Override
	public long size() throws IOException {
		return size;
	}

	@Override
	public long position() {
		return ptr;
	}

	@Override
	public int read(byte[] b
		if (len == 0)
			return 0;
		if (ptr == size)
			return -1;

		if (origIn != null) {
			int r = origIn.read(b
			if (0 < r)
				ptr += r;
			return r;
		}

		int r = 0;
		while (0 < len) {
			final long blockId = (int) (ptr / blockSize);
			final int p = (int) (ptr - blockId * blockSize);
			if (blockBufferId != blockId) {
				final long bPos = HDR_SZ + blockId * (blockSize + 4);
				final int bLen;

				if (blockId == size / blockSize && (size % blockSize) != 0)
					bLen = (int) (size % blockSize) + 4;
				else
					bLen = blockSize + 4;

				seekIn.seek(bPos);
				IO.readFully(seekIn

				crc32.reset();
				crc32.update(blockBuffer
				if (crc32.getValue() == NB.decodeUInt32(blockBuffer
					blockBufferId = blockId;
					blockBufferLen = bLen;
				} else {
					deleteCorruptBase();
					initSeekIn();
					continue;
				}
			}

			final int n = Math.min(blockBufferLen - 4
			System.arraycopy(blockBuffer

			off += n;
			len -= n;
			r += n;
			ptr += n;
		}
		return r;
	}

	@Override
	public void close() throws IOException {
		if (origIn != null)
			origIn.close();
		if (seekIn != null)
			seekIn.close();
	}

	private void initSeekIn() throws IOException
			FileNotFoundException {
		basePath = wc.db.deltaBaseCacheEntry(baseId);
		byte[] hdr = new byte[HDR_SZ];
		for (;;) {
			try {
				seekIn = SeekableInputStream.open(basePath);
			} catch (FileNotFoundException notFound) {
				extractBase();
				continue;
			}

			IO.readFully(seekIn
					|| 1 != NB.decodeInt32(hdr
					|| size != NB.decodeUInt64(hdr
				deleteCorruptBase();
				continue;
			}
			break;
		}

		blockSize = NB.decodeInt32(hdr
		blockBuffer = new byte[blockSize + 4];
		blockBufferId = -1;
	}

	private void deleteCorruptBase() throws IOException {
		seekIn.close();
		seekIn = null;

		if (!basePath.delete() && basePath.exists())
			throw new CorruptObjectException(baseId
					JGitText.get().cannotReadObject);
	}

	private void extractBase() throws IOException
			FileNotFoundException {
		File dir = basePath.getParentFile();
		if (!dir.exists())
			dir.mkdirs();

		boolean ok = false;
		File tmp = File.createTempFile("base"
		try {
			ObjectStream in = packFile.load(wc
			try {
				MessageDigest md = Constants.newMessageDigest();
				md.update(Constants.encodedTypeString(in.getType()));
				md.update((byte) ' ');
				md.update(Constants.encodeASCII(size));
				md.update((byte) 0);

				long cnt = 0;
				FileOutputStream out = new FileOutputStream(tmp);
				try {
					byte[] buf = new byte[blockSize + 4];
					System.arraycopy(MAGIC
					NB.encodeInt32(buf
					NB.encodeInt32(buf
					NB.encodeInt64(buf
					out.write(buf

					while (cnt < size) {
						int n = (int) Math.min(blockSize
						IO.readFully(in

						crc32.reset();
						crc32.update(buf
						NB.encodeInt32(buf
						md.update(buf

						out.write(buf
						cnt += n;
					}
				} finally {
					out.close();
				}

				if (!baseId.equals(ObjectId.fromRaw(md.digest())))
					throw new CorruptObjectException(baseId
							JGitText.get().cannotReadObject);

				if (size == cnt)
					ok = true;
			} finally {
				in.close();
			}

			if (ok) {
				tmp.setReadOnly();
				ok = tmp.renameTo(basePath);
			}
		} finally {
			if (!ok)
				tmp.delete();
		}
	}
}
