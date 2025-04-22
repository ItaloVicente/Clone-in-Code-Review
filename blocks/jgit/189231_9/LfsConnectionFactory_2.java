package org.eclipse.jgit.lfs.internal;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lfs.errors.LfsConfigInvalidException;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.BlobBasedConfig;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.TreeWalk;

public class LfsConfig {
	private Repository db;

	private Config delegate;

	public LfsConfig(Repository db) throws LfsConfigInvalidException {
		this.db = db;
		delegate = this.createDelegateConfig();
	}

	private Config createDelegateConfig()
			throws LfsConfigInvalidException {
		Config result = null;

		if (!db.isBare()) {
			result = createLfsConfigFromWorkingTree();
			if (result == null) {
				result = createLfsConfigFromIndex();
			}
		}

		if (result == null) {
			result = createLfsConfigFromHeadRevision();
		}

		if (result == null) {
			result = createEmptyConfig();
		}

		return result;
	}

	private FileBasedConfig createLfsConfigFromWorkingTree()
			throws LfsConfigInvalidException {
		File lfsConfig = db.getFS().resolve(db.getWorkTree()
				Constants.DOT_LFS_CONFIG);
		if (lfsConfig.exists() && lfsConfig.isFile()) {
			FileBasedConfig config = new FileBasedConfig(lfsConfig
			try {
				config.load();
				return config;
			} catch (ConfigInvalidException | IOException e) {
				throw new LfsConfigInvalidException(
						LfsText.get().dotLfsConfigReadFailed
			}
		}
		return null;
	}

	private Config createLfsConfigFromIndex()
			throws LfsConfigInvalidException {
		try {
			DirCacheEntry entry = db.readDirCache()
					.getEntry(Constants.DOT_LFS_CONFIG);
			if (entry != null) {
				return new BlobBasedConfig(null
			}
		} catch (NoWorkTreeException | IOException | ConfigInvalidException e) {
			throw new LfsConfigInvalidException(
					LfsText.get().dotLfsConfigReadFailed
		}
		return null;
	}

	private Config createLfsConfigFromHeadRevision()
			throws LfsConfigInvalidException {
		try (RevWalk revWalk = new RevWalk(db)) {
			ObjectId headCommitId = db
					.resolve(org.eclipse.jgit.lib.Constants.HEAD);
			RevCommit commit = revWalk.parseCommit(headCommitId);
			RevTree tree = commit.getTree();
			TreeWalk treewalk = TreeWalk.forPath(db
					tree);
			if (treewalk != null) {
				return new BlobBasedConfig(null
			}
		} catch (RevisionSyntaxException | IOException
				| ConfigInvalidException e) {
			throw new LfsConfigInvalidException(
					LfsText.get().dotLfsConfigReadFailed
		}
		return null;
	}

	private Config createEmptyConfig() {
		return new Config();
	}

	public String getString(final String section
			final String name) {
		return delegate.getString(section
	}
}
