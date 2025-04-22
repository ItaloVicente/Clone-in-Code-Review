
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.internal.storage.file.ObjectDirectory.AlternateHandle;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.util.FS;

class CachedObjectDirectory extends FileObjectDatabase {
	private ObjectIdOwnerMap<UnpackedObjectId> unpackedObjects;

	private final ObjectDirectory wrapped;

	private CachedObjectDirectory[] alts;

	CachedObjectDirectory(ObjectDirectory wrapped) {
		this.wrapped = wrapped;
		this.unpackedObjects = scanLoose();
	}

	private ObjectIdOwnerMap<UnpackedObjectId> scanLoose() {
		ObjectIdOwnerMap<UnpackedObjectId> m = new ObjectIdOwnerMap<>();
		File objects = wrapped.getDirectory();
		String[] fanout = objects.list();
		if (fanout == null)
			return m;
		for (String d : fanout) {
			if (d.length() != 2)
				continue;
			String[] entries = new File(objects
			if (entries == null)
				continue;
			for (String e : entries) {
				if (e.length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
					continue;
				try {
					ObjectId id = ObjectId.fromString(d + e);
					m.add(new UnpackedObjectId(id));
				} catch (IllegalArgumentException notAnObject) {
				}
			}
		}
		return m;
	}

	@Override
	public void close() {
	}

	@Override
	public ObjectDatabase newCachedDatabase() {
		return this;
	}

	@Override
	File getDirectory() {
		return wrapped.getDirectory();
	}

	@Override
	File fileFor(AnyObjectId id) {
		return wrapped.fileFor(id);
	}

	@Override
	Config getConfig() {
		return wrapped.getConfig();
	}

	@Override
	FS getFS() {
		return wrapped.getFS();
	}

	@Override
	Set<ObjectId> getShallowCommits() throws IOException {
		return wrapped.getShallowCommits();
	}

	private CachedObjectDirectory[] myAlternates() {
		if (alts == null) {
			ObjectDirectory.AlternateHandle[] src = wrapped.myAlternates();
			alts = new CachedObjectDirectory[src.length];
			for (int i = 0; i < alts.length; i++)
				alts[i] = src[i].db.newCachedFileObjectDatabase();
		}
		return alts;
	}

	private Set<AlternateHandle.Id> skipMe(Set<AlternateHandle.Id> skips) {
		Set<AlternateHandle.Id> withMe = new HashSet<>();
		if (skips != null) {
			withMe.addAll(skips);
		}
		withMe.add(getAlternateId());
		return withMe;
	}

	@Override
	void resolve(Set<ObjectId> matches
			throws IOException {
		wrapped.resolve(matches
	}

	@Override
	public boolean has(AnyObjectId objectId) throws IOException {
		return has(objectId
	}

	private boolean has(AnyObjectId objectId
			throws IOException {
		if (unpackedObjects.contains(objectId)) {
			return true;
		}
		if (wrapped.hasPackedObject(objectId)) {
			return true;
		}
		skips = skipMe(skips);
		for (CachedObjectDirectory alt : myAlternates()) {
			if (!skips.contains(alt.getAlternateId())) {
				if (alt.has(objectId
					return true;
				}
			}
		}
		return false;
	}

	@Override
	ObjectLoader openObject(WindowCursor curs
			throws IOException {
		return openObject(curs
	}

	private ObjectLoader openObject(final WindowCursor curs
			final AnyObjectId objectId
			throws IOException {
		ObjectLoader ldr = openLooseObject(curs
		if (ldr != null) {
			return ldr;
		}
		ldr = wrapped.openPackedObject(curs
		if (ldr != null) {
			return ldr;
		}
		skips = skipMe(skips);
		for (CachedObjectDirectory alt : myAlternates()) {
			if (!skips.contains(alt.getAlternateId())) {
				ldr = alt.openObject(curs
				if (ldr != null) {
					return ldr;
				}
			}
		}
		return null;
	}

	@Override
	long getObjectSize(WindowCursor curs
			throws IOException {
		return wrapped.getObjectSize(curs
	}

	@Override
	ObjectLoader openLooseObject(WindowCursor curs
			throws IOException {
		if (unpackedObjects.contains(id)) {
			ObjectLoader ldr = wrapped.openLooseObject(curs
			if (ldr != null)
				return ldr;
			unpackedObjects = scanLoose();
		}
		return null;
	}

	@Override
	InsertLooseObjectResult insertUnpackedObject(File tmp
			boolean createDuplicate) throws IOException {
		InsertLooseObjectResult result = wrapped.insertUnpackedObject(tmp
				objectId
		switch (result) {
		case INSERTED:
		case EXISTS_LOOSE:
			unpackedObjects.addIfAbsent(new UnpackedObjectId(objectId));
			break;

		case EXISTS_PACKED:
		case FAILURE:
			break;
		}
		return result;
	}

	@Override
	PackFile openPack(File pack) throws IOException {
		return wrapped.openPack(pack);
	}

	@Override
	void selectObjectRepresentation(PackWriter packer
			WindowCursor curs) throws IOException {
		wrapped.selectObjectRepresentation(packer
	}

	@Override
	Collection<PackFile> getPacks() {
		return wrapped.getPacks();
	}

	private static class UnpackedObjectId extends ObjectIdOwnerMap.Entry {
		UnpackedObjectId(AnyObjectId id) {
			super(id);
		}
	}

	private AlternateHandle.Id getAlternateId() {
		return wrapped.getAlternateId();
	}
}
