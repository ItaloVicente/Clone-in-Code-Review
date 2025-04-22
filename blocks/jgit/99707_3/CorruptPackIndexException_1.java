
package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.pack.CorruptPackIndexException;
import org.eclipse.jgit.internal.storage.pack.FsckPackParser;
import org.eclipse.jgit.internal.storage.pack.FsckPackParser.CorruptIndex;
import org.eclipse.jgit.internal.storage.pack.FsckPackParser.CorruptObject;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.transport.PackedObjectInfo;

public class DfsFsck {
	private final DfsRepository repo;

	private final DfsObjDatabase objdb;

	private final DfsReader ctx;

	private final ObjectChecker objChecker;

	private final Set<CorruptObject> corruptObjects = new HashSet<>();

	private final Set<ObjectId> missingObjects = new HashSet<>();

	private final Set<CorruptIndex> corruptedIndex = new HashSet<>();

	public DfsFsck(DfsRepository repository
		repo = repository;
		objChecker = objectChecker;
		objdb = repo.getObjectDatabase();
		ctx = objdb.newReader();
	}

	public DfsFsck(DfsRepository repository) {
		this(repository
	}

	public void check(ProgressMonitor pm) throws IOException {
		if (pm == null) {
			pm = NullProgressMonitor.INSTANCE;
		}

		try {
			for (DfsPackFile pack : objdb.getPacks()) {
				DfsPackDescription packDesc = pack.getPackDescription();
				List<PackedObjectInfo> objectsInPack;
				try (ReadableChannel channel =
						repo.getObjectDatabase()
								.openFile(packDesc
										PackExt.PACK)) {
					FsckPackParser parser = new FsckPackParser(repo
							Channels.newInputStream(channel)
							packDesc.getObjectCount());
					parser.parse(pm);
					corruptObjects.addAll(parser.getCorruptObjects());
					objectsInPack = parser.getSortedObjectList(null);
					parser.verifyIndex(objectsInPack

					ObjectWalk objectWalk = new ObjectWalk(ctx);
					for (PackedObjectInfo o : objectsInPack) {
						RevObject object = objectWalk.parseAny(o);
						objectWalk.markStart(object);
						objectWalk.checkConnectivity();
						objectWalk.markUninteresting(object);
					}
				} catch (MissingObjectException e) {
					missingObjects.add(e.getObjectId());
				} catch (CorruptPackIndexException e) {
					corruptedIndex.add(new CorruptIndex(pack.getPackName()
							e.getErrorType()));
				}
			}
		} finally {
			ctx.close();
		}
	}

	public Set<CorruptObject> getCorruptObjects() {
		return corruptObjects;
	}

	public Set<ObjectId> getMissingObjects() {
		return missingObjects;
	}

	public Set<CorruptIndex> getCorruptedIndex() {
		return corruptedIndex;
	}
}
