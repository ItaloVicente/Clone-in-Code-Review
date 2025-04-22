
package org.eclipse.jgit.storage.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.zip.DeflaterOutputStream;

import org.eclipse.jgit.errors.ObjectWritingException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.TemporaryBuffer;

class UnpackingPackParser extends PackParser {
	private final FileObjectDatabase db;

	private final ObjectDirectoryInserter inserter;

	private final boolean fsync;

	private File objTmp;

	private FileOutputStream objOut;

	private DeflaterOutputStream objDeflate;

	private boolean inDelta;

	private TemporaryBuffer.LocalFile deltaBuf;

	private long deltaBufSize;

	private long deltaOffset;

	private InputStream deltaIn;

	UnpackingPackParser(FileObjectDatabase db
			ObjectDirectoryInserter inserter
		super(db
		setCheckObjectCollisions(false);

		this.db = db;
		this.inserter = inserter;
		this.fsync = inserter.getFSyncObjectFiles();
	}

	@Override
	public PackLock parse(ProgressMonitor progress) throws IOException {
		try {
			return super.parse(progress);

		} finally {
			if (deltaIn != null)
				deltaIn.close();

			if (deltaBuf != null) {
				deltaBuf.close();
				deltaBuf.destroy();
			}

			if (objOut != null)
				objOut.close();

			if (objTmp != null)
				objTmp.delete();
		}
	}

	@Override
	protected void onBeginWholeObject(long streamPosition
			long inflatedSize) throws IOException {
		beginObject(type
	}

	@Override
	protected void onEndWholeObject(PackedObjectInfo info) throws IOException {
		endObject(info);
	}

	private void beginObject(int type
			FileNotFoundException {
		objTmp = inserter.newTempFile();
		objOut = new FileOutputStream(objTmp);
		OutputStream out = objOut;
		if (fsync)
			out = Channels.newOutputStream(objOut.getChannel());
		objDeflate = inserter.compress(out);
		inserter.writeHeader(objDeflate
	}

	private void endObject(AnyObjectId objectId) throws IOException
			ObjectWritingException {
		objDeflate.finish();
		objDeflate.flush();
		if (fsync)
			objOut.getChannel().force(true);
		objOut.close();

		objDeflate = null;
		objOut = null;

		ObjectId id = objectId.copy();
		switch (db.insertUnpackedObject(objTmp
		case INSERTED:
		case EXISTS_PACKED:
		case EXISTS_LOOSE:
			objTmp = null;
			break;

		case FAILURE:
		default:
			objTmp = null;
			throw new ObjectWritingException("Unable to create new object: "
					+ db.fileFor(id));
		}
	}

	@Override
	protected void onBeginRefDelta(long deltaStreamPosition
			AnyObjectId baseId
		onBeginDelta();
	}

	@Override
	protected void onBeginOfsDelta(long deltaStreamPosition
			long baseStreamPosition
		onBeginDelta();
	}

	private void onBeginDelta() {
		if (deltaBuf == null) {
			File dir = db.getDirectory();
			int limit = 1024 * 1024;
			deltaBuf = new TemporaryBuffer.LocalFile(dir
		}
		deltaOffset = deltaBufSize;
		inDelta = true;
	}

	@Override
	protected void onInflatedData(byte[] raw
			throws IOException {
		if (objDeflate != null)
			objDeflate.write(raw
	}

	@Override
	protected void onObjectHeader(Source src
			throws IOException {
		if (inDelta && src == Source.INPUT) {
			deltaBuf.write(raw
			deltaBufSize += len;
		}
	}

	@Override
	protected void onObjectData(Source src
			throws IOException {
		if (inDelta && src == Source.INPUT) {
			deltaBuf.write(raw
			deltaBufSize += len;
		}
	}

	@Override
	protected UnresolvedDelta onEndDelta() throws IOException {
		inDelta = false;
		return new DeferredDelta(deltaOffset);
	}

	@Override
	protected void onResolvedDelta(AnyObjectId id
			throws IOException {
		beginObject(type
		objDeflate.write(data);
		endObject(id);
	}

	@Override
	protected void onPackFooter(byte[] hash) throws IOException {
		if (deltaBuf != null)
			deltaBuf.close();
	}

	private static class DeferredDelta extends UnresolvedDelta {
		final long bufferOffset;

		DeferredDelta(long bufferOffset) {
			this.bufferOffset = bufferOffset;
		}
	}

	@Override
	protected ObjectTypeAndSize seekDatabase(UnresolvedDelta delta
			ObjectTypeAndSize info) throws IOException {
		DeferredDelta d = (DeferredDelta) delta;
		if (deltaIn instanceof FileInputStream) {
			((FileInputStream) deltaIn).getChannel().position(d.bufferOffset);
		} else {
			if (deltaIn != null)
				deltaIn.close();
			deltaIn = deltaBuf.openInputStream();
			IO.skipFully(deltaIn
		}
		return readObjectHeader(info);
	}

	@Override
	protected int readDatabase(byte[] dst
		return deltaIn.read(dst
	}

	@Override
	protected boolean checkCRC(int oldCRC) {
	}

	@Override
	protected ObjectTypeAndSize seekDatabase(PackedObjectInfo obj
			ObjectTypeAndSize info) throws IOException {
		return null;
	}

	@Override
	protected void onStoreStream(byte[] raw
			throws IOException {
	}

	@Override
	protected boolean onAppendBase(int typeCode
			PackedObjectInfo info) throws IOException {
	}

	@Override
	protected void onEndThinPack() throws IOException {
	}
}
