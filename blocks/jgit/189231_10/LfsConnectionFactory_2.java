package org.eclipse.jgit.lfs.internal;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.ConfigInvalidException;
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

import static org.eclipse.jgit.lib.Constants.HEAD;

public class LfsConfig {
	private Repository db;
	private Config delegate;

	public LfsConfig(Repository db) throws IOException {
		this.db = db;
		delegate = this.load();
	}

	private Config load() throws IOException {
		Config result = null;

		if (!db.isBare()) {
			result = loadFromWorkingTree();
			if (result == null) {
				result = loadFromIndex();
			}
		}

		if (result == null) {
			result = loadFromHead();
		}

		if (result == null) {
			result = emptyConfig();
		}

		return result;
	}

	@Nullable
	private Config loadFromWorkingTree()
			throws IOException {
		File lfsConfig = db.getFS().resolve(db.getWorkTree()
				Constants.DOT_LFS_CONFIG);
		if (lfsConfig.exists() && lfsConfig.isFile()) {
			FileBasedConfig config = new FileBasedConfig(lfsConfig
			try {
				config.load();
				return config;
			} catch (ConfigInvalidException e) {
				throw new LfsConfigInvalidException(
						LfsText.get().dotLfsConfigReadFailed
			}
		}
		return null;
	}

	@Nullable
	private Config loadFromIndex()
			throws IOException {
		try {
			DirCacheEntry entry = db.readDirCache()
					.getEntry(Constants.DOT_LFS_CONFIG);
			if (entry != null) {
				return new BlobBasedConfig(null
			}
		} catch (ConfigInvalidException e) {
			throw new LfsConfigInvalidException(
					LfsText.get().dotLfsConfigReadFailed
		}
		return null;
	}

	@Nullable
	private Config loadFromHead() throws IOException {
		try (RevWalk revWalk = new RevWalk(db)) {
			ObjectId headCommitId = db
					.resolve(HEAD);
			RevCommit commit = revWalk.parseCommit(headCommitId);
			RevTree tree = commit.getTree();
			TreeWalk treewalk = TreeWalk.forPath(db
					tree);
			if (treewalk != null) {
				return new BlobBasedConfig(null
			}
		} catch (ConfigInvalidException e) {
			throw new LfsConfigInvalidException(
					LfsText.get().dotLfsConfigReadFailed
		}
		return null;
	}

	private Config emptyConfig() {
		return new Config();
	}

	public String getString(final String section
			final String name) {
		String result = db.getConfig().getString(section
		if (result == null) {
			result = delegate.getString(section
		}
		return result;
	}
}
