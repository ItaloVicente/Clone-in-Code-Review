
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.StandardCopyOption;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RefDirectoryRename extends RefRename {
	private static final Logger LOG = LoggerFactory
			.getLogger(RefDirectoryRename.class);

	private final RefDirectory refdb;

	private ObjectId objId;

	private boolean updateHEAD;

	private RefDirectoryUpdate tmp;

	RefDirectoryRename(RefDirectoryUpdate src
		super(src
		refdb = src.getRefDatabase();
	}

	@Override
	protected Result doRename() throws IOException {
		if (source.getRef().isSymbolic())

		objId = source.getOldObjectId();
		updateHEAD = needToUpdateHEAD();
		tmp = refdb.newTemporaryUpdate();
		try (RevWalk rw = new RevWalk(refdb.getRepository())) {
			tmp.setNewObjectId(objId);
			tmp.setForceUpdate(true);
			tmp.disableRefLog();
			switch (tmp.update(rw)) {
			case NEW:
			case FORCED:
			case NO_CHANGE:
				break;
			default:
				return tmp.getResult();
			}

			if (!renameLog(source
				return Result.IO_FAILURE;

			RefUpdate dst = destination;
			if (updateHEAD) {
				if (!linkHEAD(destination)) {
					renameLog(tmp
					return Result.LOCK_FAILURE;
				}

				dst = refdb.newUpdate(Constants.HEAD
				dst.setRefLogIdent(destination.getRefLogIdent());
				dst.setRefLogMessage(destination.getRefLogMessage()
			}

			source.setExpectedOldObjectId(objId);
			source.setForceUpdate(true);
			source.disableRefLog();
			if (source.delete(rw) != Result.FORCED) {
				renameLog(tmp
				if (updateHEAD)
					linkHEAD(source);
				return source.getResult();
			}

			if (!renameLog(tmp
				renameLog(tmp
				source.setExpectedOldObjectId(ObjectId.zeroId());
				source.setNewObjectId(objId);
				source.update(rw);
				if (updateHEAD)
					linkHEAD(source);
				return Result.IO_FAILURE;
			}

			dst.setExpectedOldObjectId(ObjectId.zeroId());
			dst.setNewObjectId(objId);
			if (dst.update(rw) != Result.NEW) {
				if (renameLog(destination
					renameLog(tmp
				source.setExpectedOldObjectId(ObjectId.zeroId());
				source.setNewObjectId(objId);
				source.update(rw);
				if (updateHEAD)
					linkHEAD(source);
				return dst.getResult();
			}

			return Result.RENAMED;
		} finally {
			try {
				refdb.delete(tmp);
			} catch (IOException err) {
				FileUtils.delete(refdb.fileFor(tmp.getName()));
			}
		}
	}

	private boolean renameLog(RefUpdate src
		File srcLog = refdb.logFor(src.getName());
		File dstLog = refdb.logFor(dst.getName());

		if (!srcLog.exists())
			return true;

		if (!rename(srcLog
			return false;

		try {
			final int levels = RefDirectory.levelsIn(src.getName()) - 2;
			RefDirectory.delete(srcLog
			return true;
		} catch (IOException e) {
			rename(dstLog
			return false;
		}
	}

	private static boolean rename(File src
		try {
			FileUtils.rename(src
			return true;
		} catch (AtomicMoveNotSupportedException e) {
			LOG.error(e.getMessage()
		} catch (IOException e) {
		}

		File dir = dst.getParentFile();
		if ((dir.exists() || !dir.mkdirs()) && !dir.isDirectory())
			return false;
		try {
			FileUtils.rename(src
			return true;
		} catch (IOException e) {
			LOG.error(e.getMessage()
			return false;
		}
	}

	private boolean linkHEAD(RefUpdate target) {
		try {
			RefUpdate u = refdb.newUpdate(Constants.HEAD
			u.disableRefLog();
			switch (u.link(target.getName())) {
			case NEW:
			case FORCED:
			case NO_CHANGE:
				return true;
			default:
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}
}
