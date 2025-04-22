
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

	private ChunkWriter chunk;

	private WriteBuffer buffer;

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
		init();

		MessageDigest chunkDigest = Constants.newMessageDigest();
		LinkedList<ChunkKey> fragments = new LinkedList<ChunkKey>();
		ChunkWriter c = chunk.isEmpty() ? chunk : newChunk();
		int position = c.position();
		if (!c.whole(type
			throw new DhtException(DhtText.get().cannotInsertObject);

		MessageDigest md = digest();
		md.update(Constants.encodedTypeString(type));
		md.update((byte) ' ');
		md.update(Constants.encodeASCII(len));
		md.update((byte) 0);

		byte[] inBuf = buffer();
		long size = 0;
		long done = 0;
		while (done < len) {
			if (done == 0 || deflater.needsInput()) {
				int inAvail = in.read(inBuf);
				if (inAvail <= 0) {
					c.clear();
					throw new EOFException();
				}
				md.update(inBuf
				deflater.setInput(inBuf
				done += inAvail;
			}

			if (c.free() == 0) {
				size += c.size();
				fragments.add(c.putData(chunkDigest
			}
			c.deflate(deflater);
		}

		deflater.finish();

		while (!deflater.finished()) {
			if (c.free() == 0) {
				size += c.size();
				fragments.add(c.putData(chunkDigest
			}
			c.deflate(deflater);
		}
		if (!c.isEmpty()) {
			size += c.size();
			fragments.add(c.putData(chunkDigest
		}

		ObjectId objId = ObjectId.fromRaw(md.digest());

		byte[] fragmentBin = ChunkFragments.toByteArray(fragments);
		for (ChunkKey k : fragments)
			db.chunks().putFragments(k

		ChunkKey first = fragments.get(0);
		PackedObjectInfo oe = new PackedObjectInfo(objId);
		oe.setOffset(position);

		int weight = (int) Math.min(size - position
		ChunkLink link = new ChunkLink(first
		db.objectIndex().add(ObjectIndexKey.create(repo
		db.chunks().putIndex(first
				ChunkIndex.create(Collections.singletonList(oe))

		return objId;
	}

	@Override
	public ObjectId insert(int type
			throws IOException {
		if (mustFragmentSize() < len)
			return insertStream(type

		init();
		ObjectId objId = idFor(type
		if (!chunk.whole(deflater
			chunk.flush(digest());
			if (!chunk.whole(deflater
				return insertStream(type
		}
		return objId;
	}

	private int mustFragmentSize() {
		return 4 * options.getChunkSize();
	}

	@Override
	public PackParser newPackParser(InputStream in) throws IOException {
		if (buffer == null)
			buffer = db.newWriteBuffer();
		return new DhtPackParser(objdb
	}

	@Override
	public void flush() throws IOException {
		if (chunk != null)
			chunk.flush(digest());

		if (buffer != null)
			buffer.flush();
	}

	@Override
	public void release() {
		if (deflater != null) {
			deflater.end();
			deflater = null;
		}

		chunk = null;
		buffer = null;
	}

	private void init() {
		if (deflater == null)
			deflater = new Deflater(options.getCompressionLevel());
		else
			deflater.reset();

		if (buffer == null)
			buffer = db.newWriteBuffer();

		if (chunk == null)
			chunk = newChunk();
	}

	private ChunkWriter newChunk() {
		return new ChunkWriter(repo
	}

	private static ByteArrayInputStream asStream(byte[] data
		return new ByteArrayInputStream(data
	}
}
