
package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.CachedPack;
import org.eclipse.jgit.internal.storage.pack.ObjectReuseAsIs;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.PackOutputStream;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BitmapIndex;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.InflaterCache;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;

final class WindowCursor extends ObjectReader implements ObjectReuseAsIs {
	final byte[] tempId = new byte[Constants.OBJECT_ID_LENGTH];

	private Inflater inf;

	private ByteWindow window;

	private DeltaBaseCache baseCache;

	@Nullable
	private final ObjectInserter createdFromInserter;

	final FileObjectDatabase db;

	WindowCursor(FileObjectDatabase db) {
		this.db = db;
		this.createdFromInserter = null;
		this.streamFileThreshold = WindowCache.getStreamFileThreshold();
	}

	WindowCursor(FileObjectDatabase db
			@Nullable ObjectDirectoryInserter createdFromInserter) {
		this.db = db;
		this.createdFromInserter = createdFromInserter;
		this.streamFileThreshold = WindowCache.getStreamFileThreshold();
	}

	DeltaBaseCache getDeltaBaseCache() {
		if (baseCache == null)
			baseCache = new DeltaBaseCache();
		return baseCache;
	}

	@Override
	public ObjectReader newReader() {
		return new WindowCursor(db);
	}

	@Override
	public BitmapIndex getBitmapIndex() throws IOException {
		for (PackFile pack : db.getPacks()) {
			PackBitmapIndex index = pack.getBitmapIndex();
			if (index != null)
				return new BitmapIndexImpl(index);
		}
		return null;
	}

	@Override
	public Collection<CachedPack> getCachedPacksAndUpdate(
			BitmapBuilder needBitmap) throws IOException {
		for (PackFile pack : db.getPacks()) {
			PackBitmapIndex index = pack.getBitmapIndex();
			if (needBitmap.removeAllOrNone(index))
				return Collections.<CachedPack> singletonList(
						new LocalCachedPack(Collections.singletonList(pack)));
		}
		return Collections.emptyList();
	}

	@Override
	public Collection<ObjectId> resolve(AbbreviatedObjectId id)
			throws IOException {
		if (id.isComplete())
			return Collections.singleton(id.toObjectId());
		HashSet<ObjectId> matches = new HashSet<>(4);
		db.resolve(matches
		return matches;
	}

	@Override
	public boolean has(AnyObjectId objectId) throws IOException {
		return db.has(objectId);
	}

	@Override
	public ObjectLoader open(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		final ObjectLoader ldr = db.openObject(this
		if (ldr == null) {
			if (typeHint == OBJ_ANY)
				throw new MissingObjectException(objectId.copy()
						JGitText.get().unknownObjectType2);
			throw new MissingObjectException(objectId.copy()
		}
		if (typeHint != OBJ_ANY && ldr.getType() != typeHint)
			throw new IncorrectObjectTypeException(objectId.copy()
		return ldr;
	}

	@Override
	public Set<ObjectId> getShallowCommits() throws IOException {
		return db.getShallowCommits();
	}

	@Override
	public long getObjectSize(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		long sz = db.getObjectSize(this
		if (sz < 0) {
			if (typeHint == OBJ_ANY)
				throw new MissingObjectException(objectId.copy()
						JGitText.get().unknownObjectType2);
			throw new MissingObjectException(objectId.copy()
		}
		return sz;
	}

	@Override
	public LocalObjectToPack newObjectToPack(AnyObjectId objectId
		return new LocalObjectToPack(objectId
	}

	@Override
	public void selectObjectRepresentation(PackWriter packer
			ProgressMonitor monitor
			throws IOException
		for (ObjectToPack otp : objects) {
			db.selectObjectRepresentation(packer
			monitor.update(1);
		}
	}

	@Override
	public void copyObjectAsIs(PackOutputStream out
			boolean validate) throws IOException
			StoredObjectRepresentationNotAvailableException {
		LocalObjectToPack src = (LocalObjectToPack) otp;
		src.pack.copyAsIs(out
	}

	@Override
	public void writeObjects(PackOutputStream out
			throws IOException {
		for (ObjectToPack otp : list)
			out.writeObject(otp);
	}

	int copy(final PackFile pack
			int dstoff
		final long length = pack.length;
		int need = cnt;
		while (need > 0 && position < length) {
			pin(pack
			final int r = window.copy(position
			position += r;
			dstoff += r;
			need -= r;
		}
		return cnt - need;
	}

	@Override
	public void copyPackAsIs(PackOutputStream out
			throws IOException {
		((LocalCachedPack) pack).copyAsIs(out
	}

	void copyPackAsIs(final PackFile pack
			final PackOutputStream out) throws IOException {
		long position = 12;
		long remaining = length - (12 + 20);
		while (0 < remaining) {
			pin(pack

			int ptr = (int) (position - window.start);
			int n = (int) Math.min(window.size() - ptr
			window.write(out
			position += n;
			remaining -= n;
		}
	}

	int inflate(final PackFile pack
			boolean headerOnly) throws IOException
		prepareInflater();
		pin(pack
		position += window.setInput(position
		for (int dstoff = 0;;) {
			int n = inf.inflate(dstbuf
			dstoff += n;
			if (inf.finished() || (headerOnly && dstoff == dstbuf.length))
				return dstoff;
			if (inf.needsInput()) {
				pin(pack
				position += window.setInput(position
			} else if (n == 0)
				throw new DataFormatException();
		}
	}

	ByteArrayWindow quickCopy(PackFile p
			throws IOException {
		pin(p
		if (window instanceof ByteArrayWindow
				&& window.contains(p
			return (ByteArrayWindow) window;
		return null;
	}

	Inflater inflater() {
		prepareInflater();
		return inf;
	}

	private void prepareInflater() {
		if (inf == null)
			inf = InflaterCache.get();
		else
			inf.reset();
	}

	void pin(PackFile pack
			throws IOException {
		final ByteWindow w = window;
		if (w == null || !w.contains(pack
			window = null;
			window = WindowCache.get(pack
		}
	}

	@Override
	@Nullable
	public ObjectInserter getCreatedFromInserter() {
		return createdFromInserter;
	}

	@Override
	public void close() {
		window = null;
		baseCache = null;
		try {
			InflaterCache.release(inf);
		} finally {
			inf = null;
		}
	}
}
