
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.util.FS;

abstract class FileObjectDatabase extends ObjectDatabase {
	static enum InsertLooseObjectResult {
		INSERTED
	}

	@Override
	public ObjectReader newReader() {
		return new WindowCursor(this);
	}

	@Override
	public ObjectDirectoryInserter newInserter() {
		return new ObjectDirectoryInserter(this
	}

	abstract void resolve(Set<ObjectId> matches
			throws IOException;

	abstract Config getConfig();

	abstract FS getFS();

	abstract Set<ObjectId> getShallowCommits() throws IOException;

	abstract void selectObjectRepresentation(PackWriter packer
			ObjectToPack otp

	abstract File getDirectory();

	abstract File fileFor(AnyObjectId id);

	abstract ObjectLoader openObject(WindowCursor curs
			throws IOException;

	abstract long getObjectSize(WindowCursor curs
			throws IOException;

	abstract ObjectLoader openLooseObject(WindowCursor curs
			throws IOException;

	abstract InsertLooseObjectResult insertUnpackedObject(File tmp
			ObjectId id

	abstract PackFile openPack(File pack) throws IOException;

	abstract Collection<PackFile> getPacks();
}
