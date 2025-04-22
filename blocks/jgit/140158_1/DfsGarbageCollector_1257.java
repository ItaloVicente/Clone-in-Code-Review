
package org.eclipse.jgit.internal.storage.dfs;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.internal.storage.pack.PackExt.INDEX;
import static org.eclipse.jgit.internal.storage.pack.PackExt.PACK;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jgit.errors.CorruptPackIndexException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.fsck.FsckError;
import org.eclipse.jgit.internal.fsck.FsckError.CorruptIndex;
import org.eclipse.jgit.internal.fsck.FsckError.CorruptObject;
import org.eclipse.jgit.internal.fsck.FsckPackParser;
import org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource;
import org.eclipse.jgit.internal.submodule.SubmoduleValidator;
import org.eclipse.jgit.internal.submodule.SubmoduleValidator.SubmoduleValidationException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.GitmoduleEntry;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevObject;

public class DfsFsck {
	private final DfsRepository repo;
	private final DfsObjDatabase objdb;
	private ObjectChecker objChecker = new ObjectChecker();
	private boolean connectivityOnly;

	public DfsFsck(DfsRepository repository) {
		repo = repository;
		objdb = repo.getObjectDatabase();
	}

	public FsckError check(ProgressMonitor pm) throws IOException {
		if (pm == null) {
			pm = NullProgressMonitor.INSTANCE;
		}

		FsckError errors = new FsckError();
		if (!connectivityOnly) {
			objChecker.reset();
			checkPacks(pm
		}
		checkConnectivity(pm
		return errors;
	}

	private void checkPacks(ProgressMonitor pm
			throws IOException
		try (DfsReader ctx = objdb.newReader()) {
			for (DfsPackFile pack : objdb.getPacks()) {
				DfsPackDescription packDesc = pack.getPackDescription();
				if (packDesc.getPackSource()
						== PackSource.UNREACHABLE_GARBAGE) {
					continue;
				}
				try (ReadableChannel rc = objdb.openFile(packDesc
					verifyPack(pm
				} catch (MissingObjectException e) {
					errors.getMissingObjects().add(e.getObjectId());
				} catch (CorruptPackIndexException e) {
					errors.getCorruptIndices().add(new CorruptIndex(
							pack.getPackDescription().getFileName(INDEX)
							e.getErrorType()));
				}
			}
		}

		checkGitModules(pm
	}

	private void verifyPack(ProgressMonitor pm
			DfsPackFile pack
					throws IOException
		FsckPackParser fpp = new FsckPackParser(objdb
		fpp.setObjectChecker(objChecker);
		fpp.overwriteObjectCount(pack.getPackDescription().getObjectCount());
		fpp.parse(pm);
		errors.getCorruptObjects().addAll(fpp.getCorruptObjects());

		fpp.verifyIndex(pack.getPackIndex(ctx));
	}

	private void checkGitModules(ProgressMonitor pm
			throws IOException {
		pm.beginTask(JGitText.get().validatingGitModules
				objChecker.getGitsubmodules().size());
		for (GitmoduleEntry entry : objChecker.getGitsubmodules()) {
			AnyObjectId blobId = entry.getBlobId();
			ObjectLoader blob = objdb.open(blobId

			try {
				SubmoduleValidator.assertValidGitModulesFile(
						new String(blob.getBytes()
			} catch (SubmoduleValidationException e) {
				CorruptObject co = new FsckError.CorruptObject(
						blobId.toObjectId()
						e.getFsckMessageId());
				errors.getCorruptObjects().add(co);
			}
			pm.update(1);
		}
		pm.endTask();
	}

	private void checkConnectivity(ProgressMonitor pm
			throws IOException {
		pm.beginTask(JGitText.get().countingObjects
		try (ObjectWalk ow = new ObjectWalk(repo)) {
			for (Ref r : repo.getRefDatabase().getRefs()) {
				ObjectId objectId = r.getObjectId();
				if (objectId == null) {
					continue;
				}
				RevObject tip;
				try {
					tip = ow.parseAny(objectId);
					if (r.getLeaf().getName().startsWith(Constants.R_HEADS)
							&& tip.getType() != Constants.OBJ_COMMIT) {
						errors.getNonCommitHeads().add(r.getLeaf().getName());
					}
					ow.markStart(tip);
				} catch (MissingObjectException e) {
					errors.getMissingObjects().add(e.getObjectId());
					continue;
				}
			}
			try {
				ow.checkConnectivity();
			} catch (MissingObjectException e) {
				errors.getMissingObjects().add(e.getObjectId());
			}
		}
		pm.endTask();
	}

	public void setObjectChecker(ObjectChecker objChecker) {
		this.objChecker = objChecker;
	}

	public void setConnectivityOnly(boolean connectivityOnly) {
		this.connectivityOnly = connectivityOnly;
	}
}
