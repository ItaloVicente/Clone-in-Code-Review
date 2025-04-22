
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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

class LooseObjects {
	private static final Logger LOG = LoggerFactory
			.getLogger(LooseObjects.class);

	private final File directory;

	private final UnpackedObjectCache unpackedObjectCache;

	LooseObjects(File dir) {
		directory = dir;
		unpackedObjectCache = new UnpackedObjectCache();
	}

	File getDirectory() {
		return directory;
	}

	void create() throws IOException {
		FileUtils.mkdirs(directory);
	}

	void close() {
		unpackedObjectCache.clear();
	}

	@Override
	public String toString() {
	}

	boolean hasCached(AnyObjectId id) {
		return unpackedObjectCache.isUnpacked(id);
	}

	boolean has(AnyObjectId objectId) {
		return fileFor(objectId).exists();
	}

	boolean resolve(Set<ObjectId> matches
			int matchLimit) {
		String fanOut = id.name().substring(0
		String[] entries = new File(directory
		if (entries != null) {
			for (String e : entries) {
				if (e.length() != Constants.OBJECT_ID_STRING_LENGTH - 2) {
					continue;
				}
				try {
					ObjectId entId = ObjectId.fromString(fanOut + e);
					if (id.prefixCompare(entId) == 0) {
						matches.add(entId);
					}
				} catch (IllegalArgumentException notId) {
					continue;
				}
				if (matches.size() > matchLimit) {
					return false;
				}
			}
		}
		return true;
	}

	ObjectLoader open(WindowCursor curs
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

	long getSize(WindowCursor curs
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

	InsertLooseObjectResult insert(File tmp
		final File dst = fileFor(id);
		if (dst.exists()) {
			FileUtils.delete(tmp
			return InsertLooseObjectResult.EXISTS_LOOSE;
		}

		try {
			return tryMove(tmp
		} catch (NoSuchFileException e) {
			FileUtils.mkdir(dst.getParentFile()
		} catch (IOException e) {
			LOG.error(e.getMessage()
			FileUtils.delete(tmp
			return InsertLooseObjectResult.FAILURE;
		}

		try {
			return tryMove(tmp
		} catch (IOException e) {
			LOG.error(e.getMessage()
			FileUtils.delete(tmp
			return InsertLooseObjectResult.FAILURE;
		}
	}

	private InsertLooseObjectResult tryMove(File tmp
			throws IOException {
		Files.move(FileUtils.toPath(tmp)
				StandardCopyOption.ATOMIC_MOVE);
		dst.setReadOnly();
		unpackedObjectCache.add(id);
		return InsertLooseObjectResult.INSERTED;
	}

	File fileFor(AnyObjectId objectId) {
		String n = objectId.name();
		String d = n.substring(0
		String f = n.substring(2);
		return new File(new File(getDirectory()
	}
}
