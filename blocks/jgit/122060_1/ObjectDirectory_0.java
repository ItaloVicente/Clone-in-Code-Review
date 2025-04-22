
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Set;

import org.eclipse.jgit.internal.storage.file.FileObjectDatabase.InsertLooseObjectResult;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LooseObjects {
	private final static Logger LOG =
			LoggerFactory.getLogger(LooseObjects.class);

	protected final File objects;

	protected final UnpackedObjectCache unpackedObjectCache;

	public LooseObjects(File dir) {
		objects = dir;
		unpackedObjectCache = new UnpackedObjectCache();
	}

	public final File getDirectory() {
		return objects;
	}

	public void create() throws IOException {
		FileUtils.mkdirs(objects);
	}

	public void close() {
		unpackedObjectCache.clear();
	}

	@Override
	public String toString() {
	}

	public boolean hasCached(AnyObjectId id) {
		return unpackedObjectCache.isUnpacked(id);
	}

	public boolean has(AnyObjectId objectId) {
		return fileFor(objectId).exists();
	}

	public boolean resolve(Set<ObjectId> matches
			int limit) throws IOException {
		String fanOut = id.name().substring(0
		String[] entries = new File(getDirectory()
		if (entries != null) {
			for (String e : entries) {
				if (e.length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
					continue;
				try {
					ObjectId entId = ObjectId.fromString(fanOut + e);
					if (id.prefixCompare(entId) == 0)
						matches.add(entId);
				} catch (IllegalArgumentException notId) {
					continue;
				}
				if (matches.size() > limit)
					return false;
			}
		}
		return true;
	}

	public ObjectLoader open(WindowCursor curs
			throws IOException {
		File path = fileFor(id);
		try (FileInputStream in = new FileInputStream(path)) {
			unpackedObjectCache.add(id);
			return UnpackedObject.open(in
		} catch (FileNotFoundException noFile) {
			if (path.exists()) {
				throw noFile;
			}
			unpackedObjectCache.remove(id);
			return null;
		}
	}

	public long getSize(WindowCursor curs
			throws IOException {
		File f = fileFor(id);
		try (FileInputStream in = new FileInputStream(f)) {
			unpackedObjectCache.add(id);
			return UnpackedObject.getSize(in
		} catch (FileNotFoundException noFile) {
			if (f.exists()) {
				throw noFile;
			}
			unpackedObjectCache.remove(id);
			return -1;
		}
	}

	public InsertLooseObjectResult insert(File tmp
			boolean createDuplicate) throws IOException {
		File dst = fileFor(id);
		if (dst.exists()) {
			FileUtils.delete(tmp
			return InsertLooseObjectResult.EXISTS_LOOSE;
		}
		try {
			Files.move(FileUtils.toPath(tmp)
					StandardCopyOption.ATOMIC_MOVE);
			dst.setReadOnly();
			unpackedObjectCache.add(id);
			return InsertLooseObjectResult.INSERTED;
		} catch (AtomicMoveNotSupportedException e) {
			LOG.error(e.getMessage()
		} catch (IOException e) {
		}

		FileUtils.mkdir(dst.getParentFile()
		try {
			Files.move(FileUtils.toPath(tmp)
					StandardCopyOption.ATOMIC_MOVE);
			dst.setReadOnly();
			unpackedObjectCache.add(id);
			return InsertLooseObjectResult.INSERTED;
		} catch (AtomicMoveNotSupportedException e) {
			LOG.error(e.getMessage()
		} catch (IOException e) {
			LOG.debug(e.getMessage()
		}
		return InsertLooseObjectResult.FAILURE;
	}

	File fileFor(AnyObjectId objectId) {
		String n = objectId.name();
		String d = n.substring(0
		String f = n.substring(2);
		return new File(new File(getDirectory()
	}
}
