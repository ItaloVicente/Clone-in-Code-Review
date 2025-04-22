
package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.errors.CorruptPackIndexException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.fsck.Fsck;
import org.eclipse.jgit.internal.fsck.FsckError;
import org.eclipse.jgit.internal.fsck.FsckError.CorruptIndex;
import org.eclipse.jgit.internal.fsck.FsckPackParser;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.transport.PackedObjectInfo;

public class DfsFsck implements Fsck {
	private final DfsRepository repo;

	private final DfsObjDatabase objdb;

	private final DfsReader ctx;

	private ObjectChecker objChecker = new ObjectChecker();

	public DfsFsck(DfsRepository repository) {
		repo = repository;
		objdb = repo.getObjectDatabase();
		ctx = objdb.newReader();
	}

	@Override
	public FsckError check(ProgressMonitor pm) throws IOException {
		FsckError errors = new FsckError();
		try {
			for (DfsPackFile pack : objdb.getPacks()) {
				DfsPackDescription packDesc = pack.getPackDescription();
				try (ReadableChannel channel = repo.getObjectDatabase()
						.openFile(packDesc
					List<PackedObjectInfo> objectsInPack;
					FsckPackParser parser = new FsckPackParser(
							repo.getObjectDatabase()
					parser.setObjectChecker(objChecker);
					parser.overwriteObjectCount(packDesc.getObjectCount());
					parser.parse(pm);
					errors.getCorruptObjects()
							.addAll(parser.getCorruptObjects());
					objectsInPack = parser.getSortedObjectList(null);
					parser.verifyIndex(objectsInPack
				} catch (MissingObjectException e) {
					errors.getMissingObjects().add(e.getObjectId());
				} catch (CorruptPackIndexException e) {
					errors.getCorruptIndices().add(new CorruptIndex(
							pack.getPackDescription()
									.getFileName(PackExt.INDEX)
							e.getErrorType()));
				}
			}
		} finally {
			ctx.close();
		}
		return errors;
	}

	public void setObjectChecker(ObjectChecker objChecker) {
		this.objChecker = objChecker;
	}
}
