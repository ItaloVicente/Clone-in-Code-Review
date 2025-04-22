
package org.eclipse.jgit.storage.dht;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.LinkedList;
import java.util.zip.Deflater;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.IO;

class DhtInserter extends ObjectInserter {
	private final DhtObjDatabase objdb;

	private final RepositoryKey repo;

	private final Database db;

	private final DhtInserterOptions options;

	private Deflater deflater;

	private WriteBuffer dbWriteBuffer;

	private ChunkFormatter activeChunk;

	DhtInserter(DhtObjDatabase objdb
		this.objdb = objdb;
		this.repo = repo;
		this.db = db;
		this.options = DhtInserterOptions.DEFAULT;
	}

	@Override
	public ObjectId insert(int type
			throws IOException {
		if (Integer.MAX_VALUE < len || mustFragmentSize() < len)
			return insertStream(type

		byte[] tmp;
		try {
			tmp = new byte[(int) len];
		} catch (OutOfMemoryError tooLarge) {
			return insertStream(type
		}
		IO.readFully(in
		return insert(type
	}

	private ObjectId insertStream(int type
			throws IOException {


		MessageDigest chunkDigest = Constants.newMessageDigest();
		LinkedList<ChunkKey> fragmentList = new LinkedList<ChunkKey>();

		ChunkFormatter chunk = newChunk();
		int position = chunk.position();
		if (!chunk.whole(type
			throw new DhtException(DhtText.get().cannotInsertObject);

		MessageDigest objDigest = digest();
		objDigest.update(Constants.encodedTypeString(type));
		objDigest.update((byte) ' ');
		objDigest.update(Constants.encodeASCII(len));
		objDigest.update((byte) 0);

		Deflater def = deflater();
		byte[] inBuf = buffer();
		long size = 0;
		long done = 0;
		while (done < len) {
			if (done == 0 || def.needsInput()) {
				int inAvail = in.read(inBuf);
				if (inAvail <= 0)
					throw new EOFException();
				objDigest.update(inBuf
				def.setInput(inBuf
				done += inAvail;
			}

			if (chunk.free() == 0) {
				size += chunk.size();
				chunk.setObjectType(type);
				chunk.setFragment();
				fragmentList.add(chunk.end(chunkDigest));
				chunk.safePut(db
				chunk = newChunk();
			}
			chunk.appendDeflateOutput(def);
		}

		def.finish();

		while (!def.finished()) {
			if (chunk.free() == 0) {
				size += chunk.size();
				chunk.setObjectType(type);
				chunk.setFragment();
				fragmentList.add(chunk.end(chunkDigest));
				chunk.safePut(db
				chunk = newChunk();
			}
			chunk.appendDeflateOutput(def);
		}

		if (!chunk.isEmpty()) {
			size += chunk.size();
			chunk.setObjectType(type);
			chunk.setFragment();
			fragmentList.add(chunk.end(chunkDigest));
			chunk.safePut(db
		}
		chunk = null;

		ObjectId objId = ObjectId.fromRaw(objDigest.digest());

		ChunkKey first = fragmentList.get(0);
		PackedObjectInfo oe = new PackedObjectInfo(objId);
		oe.setOffset(position);

		for (ChunkKey k : fragmentList) {
			PackChunk.Members builder = new PackChunk.Members();
			builder.setChunkKey(k);
			builder.setFragments(new ChunkFragments(fragmentList));
			if (first.equals(k)) {
				builder.setChunkIndex(ChunkIndex.create(Collections
						.singletonList(oe)));
			}
			db.chunk().put(builder
		}

		int weight = (int) Math.min(size - position
		db.objectIndex().add(ObjectIndexKey.create(repo
				new ObjectInfo(first
				dbBuffer());

		return objId;
	}

	@Override
	public ObjectId insert(int type
			throws IOException {

		if (mustFragmentSize() < len)
			return insertStream(type

		ObjectId objId = idFor(type

		if (activeChunk == null)
			activeChunk = newChunk();

		if (activeChunk.whole(deflater()
			return objId;


		activeChunk.end(digest());
		activeChunk.safePut(db
		activeChunk = newChunk();

		if (activeChunk.whole(deflater()
			return objId;

		return insertStream(type
	}

	private int mustFragmentSize() {
		return 4 * options.getChunkSize();
	}

	@Override
	public PackParser newPackParser(InputStream in) throws IOException {
		return new DhtPackParser(objdb
	}

	@Override
	public void flush() throws IOException {
		if (activeChunk != null && !activeChunk.isEmpty()) {
			activeChunk.end(digest());
			activeChunk.safePut(db
			activeChunk = null;
		}

		if (dbWriteBuffer != null)
			dbWriteBuffer.flush();
	}

	@Override
	public void release() {
		if (deflater != null) {
			deflater.end();
			deflater = null;
		}

		dbWriteBuffer = null;
		activeChunk = null;
	}

	private Deflater deflater() {
		if (deflater == null)
			deflater = new Deflater(options.getCompressionLevel());
		else
			deflater.reset();
		return deflater;
	}

	private WriteBuffer dbBuffer() {
		if (dbWriteBuffer == null)
			dbWriteBuffer = db.newWriteBuffer();
		return dbWriteBuffer;
	}

	private ChunkFormatter newChunk() {
		ChunkFormatter fmt;

		fmt = new ChunkFormatter(repo
		fmt.setSource(ChunkInfo.Source.INSERT);
		return fmt;
	}

	private static ByteArrayInputStream asStream(byte[] data
		return new ByteArrayInputStream(data
	}
}
