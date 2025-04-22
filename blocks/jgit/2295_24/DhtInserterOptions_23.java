
package org.eclipse.jgit.storage.dht;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;
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

	DhtInserter(DhtObjDatabase objdb) {
		this.objdb = objdb;
		this.repo = objdb.getRepository().getRepositoryKey();
		this.db = objdb.getDatabase();
		this.options = objdb.getInserterOptions();
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

	private ObjectId insertStream(final int type
			final InputStream in) throws IOException {


		MessageDigest chunkDigest = Constants.newMessageDigest();
		LinkedList<ChunkKey> fragmentList = new LinkedList<ChunkKey>();

		ChunkFormatter chunk = newChunk();
		int position = chunk.position();
		if (!chunk.whole(type
			throw new DhtException(DhtText.get().cannotInsertObject);

		MessageDigest objDigest = digest();
		objDigest.update(Constants.encodedTypeString(type));
		objDigest.update((byte) ' ');
		objDigest.update(Constants.encodeASCII(inflatedSize));
		objDigest.update((byte) 0);

		Deflater def = deflater();
		byte[] inBuf = buffer();
		long packedSize = 0;
		long done = 0;
		while (done < inflatedSize) {
			if (done == 0 || def.needsInput()) {
				int inAvail = in.read(inBuf);
				if (inAvail <= 0)
					throw new EOFException();
				objDigest.update(inBuf
				def.setInput(inBuf
				done += inAvail;
			}

			if (chunk.free() == 0) {
				packedSize += chunk.size();
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
				packedSize += chunk.size();
				chunk.setObjectType(type);
				chunk.setFragment();
				fragmentList.add(chunk.end(chunkDigest));
				chunk.safePut(db
				chunk = newChunk();
			}
			chunk.appendDeflateOutput(def);
		}

		ObjectId objId = ObjectId.fromRaw(objDigest.digest());
		PackedObjectInfo oe = new PackedObjectInfo(objId);
		oe.setOffset(position);

		if (!chunk.isEmpty()) {
			packedSize += chunk.size();
			chunk.setObjectType(type);

			if (fragmentList.isEmpty()) {
				ChunkKey key = chunk.end(chunkDigest);
				chunk.setChunkIndex(Collections.singletonList(oe));
				chunk.safePut(db
				ObjectInfo info = new ObjectInfo(key
						packedSize
				ObjectIndexKey objKey = ObjectIndexKey.create(repo
				db.objectIndex().add(objKey
				return objId;
			}

			chunk.setFragment();
			fragmentList.add(chunk.end(chunkDigest));
			chunk.safePut(db
		}
		chunk = null;

		ChunkKey firstChunkKey = fragmentList.get(0);
		for (ChunkKey key : fragmentList) {
			PackChunk.Members builder = new PackChunk.Members();
			builder.setChunkKey(key);

			ChunkMeta meta = new ChunkMeta(key);
			meta.fragments = fragmentList;
			builder.setMeta(meta);

			if (firstChunkKey.equals(key))
				builder.setChunkIndex(ChunkIndex.create(Arrays.asList(oe)));

			db.chunk().put(builder
		}

		ObjectInfo info = new ObjectInfo(firstChunkKey
				packedSize
		ObjectIndexKey objKey = ObjectIndexKey.create(repo
		db.objectIndex().add(objKey

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
			deflater = new Deflater(options.getCompression());
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
