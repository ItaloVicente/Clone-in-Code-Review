
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.NB;

public class ObjectDirectoryPackParser extends PackParser {
	private final FileObjectDatabase db;

	private final CRC32 crc;

	private final MessageDigest tailDigest;

	private int indexVersion;

	private boolean keepEmpty;

	private File tmpPack;

	private File tmpIdx;

	private RandomAccessFile out;

	private long origEnd;

	private byte[] origHash;

	private long packEnd;

	private byte[] packHash;

	private Deflater def;

	private PackFile newPack;

	ObjectDirectoryPackParser(FileObjectDatabase odb
		super(odb
		this.db = odb;
		this.crc = new CRC32();
		this.tailDigest = Constants.newMessageDigest();

		indexVersion = db.getConfig().get(CoreConfig.KEY).getPackIndexVersion();
	}

	public void setIndexVersion(int version) {
		indexVersion = version;
	}

	public void setKeepEmpty(boolean empty) {
		keepEmpty = empty;
	}

	public PackFile getPackFile() {
		return newPack;
	}

	@Override
	public long getPackSize() {
		if (newPack == null)
			return super.getPackSize();

		File pack = newPack.getPackFile();
		long size = pack.length();
		String p = pack.getAbsolutePath();
		String i = p.substring(0
		File idx = new File(i);
		if (idx.exists() && idx.isFile())
			size += idx.length();
		return size;
	}

	@Override
	public PackLock parse(ProgressMonitor receiving
			throws IOException {
		tmpPack = File.createTempFile("incoming_"
		tmpIdx = new File(db.getDirectory()
		try {
			out = new RandomAccessFile(tmpPack

			super.parse(receiving

			out.seek(packEnd);
			out.write(packHash);
			out.getChannel().force(true);
			out.close();

			writeIdx();

			tmpPack.setReadOnly();
			tmpIdx.setReadOnly();

			return renameAndOpenPack(getLockMessage());
		} finally {
			if (def != null)
				def.end();
			try {
				if (out != null && out.getChannel().isOpen())
					out.close();
			} catch (IOException closeError) {
			}
			cleanupTemporaryFiles();
		}
	}

	@Override
	protected void onPackHeader(long objectCount) throws IOException {
	}

	@Override
	protected void onBeginWholeObject(long streamPosition
			long inflatedSize) throws IOException {
		crc.reset();
	}

	@Override
	protected void onEndWholeObject(PackedObjectInfo info) throws IOException {
		info.setCRC((int) crc.getValue());
	}

	@Override
	protected void onBeginOfsDelta(long streamPosition
			long baseStreamPosition
		crc.reset();
	}

	@Override
	protected void onBeginRefDelta(long streamPosition
			long inflatedSize) throws IOException {
		crc.reset();
	}

	@Override
	protected UnresolvedDelta onEndDelta() throws IOException {
		UnresolvedDelta delta = new UnresolvedDelta();
		delta.setCRC((int) crc.getValue());
		return delta;
	}

	@Override
	protected void onInflatedObjectData(PackedObjectInfo obj
			byte[] data) throws IOException {
	}

	@Override
	protected void onObjectHeader(Source src
			throws IOException {
		crc.update(raw
	}

	@Override
	protected void onObjectData(Source src
			throws IOException {
		crc.update(raw
	}

	@Override
	protected void onStoreStream(byte[] raw
			throws IOException {
		out.write(raw
	}

	@Override
	protected void onPackFooter(byte[] hash) throws IOException {
		packEnd = out.getFilePointer();
		origEnd = packEnd;
		origHash = hash;
		packHash = hash;
	}

	@Override
	protected ObjectTypeAndSize seekDatabase(UnresolvedDelta delta
			ObjectTypeAndSize info) throws IOException {
		out.seek(delta.getOffset());
		crc.reset();
		return readObjectHeader(info);
	}

	@Override
	protected ObjectTypeAndSize seekDatabase(PackedObjectInfo obj
			ObjectTypeAndSize info) throws IOException {
		out.seek(obj.getOffset());
		crc.reset();
		return readObjectHeader(info);
	}

	@Override
	protected int readDatabase(byte[] dst
		return out.read(dst
	}

	@Override
	protected boolean checkCRC(int oldCRC) {
		return oldCRC == (int) crc.getValue();
	}

	private static String baseName(File tmpPack) {
		String name = tmpPack.getName();
		return name.substring(0
	}

	private void cleanupTemporaryFiles() {
		if (tmpIdx != null && !tmpIdx.delete() && tmpIdx.exists())
			tmpIdx.deleteOnExit();
		if (tmpPack != null && !tmpPack.delete() && tmpPack.exists())
			tmpPack.deleteOnExit();
	}

	@Override
	protected boolean onAppendBase(final int typeCode
			final PackedObjectInfo info) throws IOException {
		info.setOffset(packEnd);

		final byte[] buf = buffer();
		int sz = data.length;
		int len = 0;
		buf[len++] = (byte) ((typeCode << 4) | sz & 15);
		sz >>>= 4;
		while (sz > 0) {
			buf[len - 1] |= 0x80;
			buf[len++] = (byte) (sz & 0x7f);
			sz >>>= 7;
		}

		tailDigest.update(buf
		crc.reset();
		crc.update(buf
		out.seek(packEnd);
		out.write(buf
		packEnd += len;

		if (def == null)
			def = new Deflater(Deflater.DEFAULT_COMPRESSION
		else
			def.reset();
		def.setInput(data);
		def.finish();

		while (!def.finished()) {
			len = def.deflate(buf);
			tailDigest.update(buf
			crc.update(buf
			out.write(buf
			packEnd += len;
		}

		info.setCRC((int) crc.getValue());
		return true;
	}

	@Override
	protected void onEndThinPack() throws IOException {
		final byte[] buf = buffer();

		final MessageDigest origDigest = Constants.newMessageDigest();
		final MessageDigest tailDigest2 = Constants.newMessageDigest();
		final MessageDigest packDigest = Constants.newMessageDigest();

		long origRemaining = origEnd;
		out.seek(0);
		out.readFully(buf
		origDigest.update(buf
		origRemaining -= 12;

		NB.encodeInt32(buf
		out.seek(0);
		out.write(buf
		packDigest.update(buf

		for (;;) {
			final int n = out.read(buf);
			if (n < 0)
				break;
			if (origRemaining != 0) {
				final int origCnt = (int) Math.min(n
				origDigest.update(buf
				origRemaining -= origCnt;
				if (origRemaining == 0)
					tailDigest2.update(buf
			} else
				tailDigest2.update(buf

			packDigest.update(buf
		}

		if (!Arrays.equals(origDigest.digest()
				.equals(tailDigest2.digest()
			throw new IOException(
					JGitText.get().packCorruptedWhileWritingToFilesystem);

		packHash = packDigest.digest();
	}

	private void writeIdx() throws IOException {
		try (FileOutputStream os = new FileOutputStream(tmpIdx)) {
			final PackIndexWriter iw;
			if (indexVersion <= 0)
				iw = PackIndexWriter.createOldestPossible(os
			else
				iw = PackIndexWriter.createVersion(os
			iw.write(list
			os.getChannel().force(true);
		}
	}

	private PackLock renameAndOpenPack(String lockMessage)
			throws IOException {
		if (!keepEmpty && getObjectCount() == 0) {
			cleanupTemporaryFiles();
			return null;
		}

		final MessageDigest d = Constants.newMessageDigest();
		final byte[] oeBytes = new byte[Constants.OBJECT_ID_LENGTH];
		for (int i = 0; i < getObjectCount(); i++) {
			final PackedObjectInfo oe = getObject(i);
			oe.copyRawTo(oeBytes
			d.update(oeBytes);
		}

		final String name = ObjectId.fromRaw(d.digest()).name();
		final File packDir = new File(db.getDirectory()
		final File finalPack = new File(packDir
		final File finalIdx = new File(packDir
		final PackLock keep = new PackLock(finalPack

		if (!packDir.exists() && !packDir.mkdir() && !packDir.exists()) {
			cleanupTemporaryFiles();
			throw new IOException(MessageFormat.format(
					JGitText.get().cannotCreateDirectory
							.getAbsolutePath()));
		}

		if (finalPack.exists()) {
			cleanupTemporaryFiles();
			return null;
		}

		if (lockMessage != null) {
			try {
				if (!keep.lock(lockMessage))
					throw new LockFailedException(finalPack
							MessageFormat.format(
									JGitText.get().cannotLockPackIn
			} catch (IOException e) {
				cleanupTemporaryFiles();
				throw e;
			}
		}

		try {
			FileUtils.rename(tmpPack
					StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			cleanupTemporaryFiles();
			keep.unlock();
			throw new IOException(MessageFormat.format(
					JGitText.get().cannotMovePackTo
		}

		try {
			FileUtils.rename(tmpIdx
		} catch (IOException e) {
			cleanupTemporaryFiles();
			keep.unlock();
			if (!finalPack.delete())
				finalPack.deleteOnExit();
			throw new IOException(MessageFormat.format(
					JGitText.get().cannotMoveIndexTo
		}

		try {
			newPack = db.openPack(finalPack);
		} catch (IOException err) {
			keep.unlock();
			if (finalPack.exists())
				FileUtils.delete(finalPack);
			if (finalIdx.exists())
				FileUtils.delete(finalIdx);
			throw err;
		}

		return lockMessage != null ? keep : null;
	}
}
