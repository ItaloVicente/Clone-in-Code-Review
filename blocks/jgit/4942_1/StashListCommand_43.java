package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.R_STASH;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.ReflogEntry;
import org.eclipse.jgit.storage.file.ReflogReader;
import org.eclipse.jgit.storage.file.ReflogWriter;
import org.eclipse.jgit.util.FileUtils;

public class StashDropCommand extends GitCommand<ObjectId> {

	private int stashRefEntry;

	private boolean all;

	public StashDropCommand(Repository repo) {
		super(repo);
	}

	public StashDropCommand setStashRef(final int stashRef) {
		if (stashRef < 0)
			throw new IllegalArgumentException();

		stashRefEntry = stashRef;
		return this;
	}

	public StashDropCommand setAll(final boolean all) {
		this.all = all;
		return this;
	}

	private Ref getRef() throws GitAPIException {
		try {
			return repo.getRef(R_STASH);
		} catch (IOException e) {
			throw new InvalidRefNameException(MessageFormat.format(
					JGitText.get().cannotRead
		}
	}

	private RefUpdate createRefUpdate(final Ref stashRef) throws IOException {
		RefUpdate update = repo.updateRef(R_STASH);
		update.disableRefLog();
		update.setExpectedOldObjectId(stashRef.getObjectId());
		update.setForceUpdate(true);
		return update;
	}

	private void deleteRef(final Ref stashRef) {
		try {
			Result result = createRefUpdate(stashRef).delete();
			if (Result.FORCED != result)
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().stashDropDeleteRefFailed
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashDropFailed
		}
	}

	private void updateRef(Ref stashRef
		try {
			RefUpdate update = createRefUpdate(stashRef);
			update.setNewObjectId(newId);
			Result result = update.update();
			switch (result) {
			case FORCED:
			case NEW:
			case NO_CHANGE:
				return;
			default:
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().updatingRefFailed
						result));
			}
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashDropFailed
		}
	}

	public ObjectId call() throws GitAPIException {
		checkCallable();

		Ref stashRef = getRef();
		if (stashRef == null)
			return null;

		if (all) {
			deleteRef(stashRef);
			return null;
		}

		ReflogReader reader = new ReflogReader(repo
		List<ReflogEntry> entries;
		try {
			entries = reader.getReverseEntries();
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashDropFailed
		}

		if (stashRefEntry >= entries.size())
			throw new JGitInternalException(
					JGitText.get().stashDropMissingReflog);

		if (entries.size() == 1) {
			deleteRef(stashRef);
			return null;
		}

		ReflogWriter writer = new ReflogWriter(repo
		String stashLockRef = ReflogWriter.refLockFor(R_STASH);
		File stashLockFile = writer.logFor(stashLockRef);
		File stashFile = writer.logFor(R_STASH);
		if (stashLockFile.exists())
			throw new JGitInternalException(JGitText.get().stashDropFailed
					new LockFailedException(stashFile));

		entries.remove(stashRefEntry);
		ObjectId entryId = ObjectId.zeroId();
		try {
			for (int i = entries.size() - 1; i >= 0; i--) {
				ReflogEntry entry = entries.get(i);
				writer.log(stashLockRef
						entry.getWho()
				entryId = entry.getNewId();
			}
			if (!stashLockFile.renameTo(stashFile)) {
				FileUtils.delete(stashFile);
				if (!stashLockFile.renameTo(stashFile))
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().couldNotWriteFile
							stashLockFile.getPath()
			}
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashDropFailed
		}
		updateRef(stashRef

		try {
			Ref newStashRef = repo.getRef(R_STASH);
			return newStashRef != null ? newStashRef.getObjectId() : null;
		} catch (IOException e) {
			throw new InvalidRefNameException(MessageFormat.format(
					JGitText.get().cannotRead
		}
	}
}
