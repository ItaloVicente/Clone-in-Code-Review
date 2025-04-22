
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectLoader.SmallObject;

public class InsertedObjectReader extends ObjectReader {
	private static class InsertedObject extends ObjectIdOwnerMap.Entry {
		private final SmallObject obj;

		private InsertedObject(AnyObjectId id
			super(id);
			this.obj = new SmallObject(type
		}
	}

	private final ObjectInserter inserter;
	private final ObjectReader reader;
	private final ObjectIdOwnerMap<InsertedObject> pending;
	
	private final int objectLimit;
	private final int byteLimit;
	private int bytes;

	InsertedObjectReader(ObjectInserter inserter
			int objectLimit
		this.inserter = inserter;
		this.reader = reader;
		this.objectLimit = objectLimit;
		this.byteLimit = byteLimit;
		pending = new ObjectIdOwnerMap<InsertedObject>();
	}

	@Override
	public ObjectReader newReader() {
		return reader.newReader();
	}

	@Override
	public Collection<ObjectId> resolve(AbbreviatedObjectId id)
			throws IOException {
		return reader.resolve(id);
	}

	@Override
	public ObjectLoader open(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		InsertedObject o = pending.get(objectId);
		if (o != null) {
			if (typeHint == OBJ_ANY || o.obj.getType() == typeHint)
				return o.obj;
			throw new IncorrectObjectTypeException(objectId.copy()
		}
		return reader.open(objectId
	}

	@Override
	public Set<ObjectId> getShallowCommits() throws IOException {
		return Collections.emptySet();
	}

	void put(AnyObjectId id
			throws IOException {
		if (byteLimit > 0 && length > byteLimit)
			return;
		int len = (int) length;
		byte[] buf = new byte[len];
		in.read(buf);
		put(id
	}

	void put(AnyObjectId id
			throws IOException {
		if (byteLimit > 0 && len > byteLimit)
			return;
		InsertedObject o = pending.get(id);
		if (o != null)
			return;
		if (objectLimit > 0 && pending.size() == objectLimit)
			flush(0);
		if (byteLimit > 0) {
			bytes += len;
			if (bytes > byteLimit)
				flush(len);
		}

		byte[] buf;
		if (off == 0 && len == data.length) {
			buf = data;
		} else {
			buf = new byte[len];
			System.arraycopy(data
		}
		pending.add(new InsertedObject(id
	}

	void flush(int len) throws IOException {
		inserter.doFlush();
		pending.clear();
		bytes = len;
	}
}
