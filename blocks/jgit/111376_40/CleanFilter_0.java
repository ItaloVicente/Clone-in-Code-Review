package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.hooks.PrePushHook;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.LFS;

public class BuiltinLFS extends LFS {

	private BuiltinLFS() {
		SmudgeFilter.register();
		CleanFilter.register();
	}

	public static void register() {
		setInstance(new BuiltinLFS());
	}

	@Override
	public boolean isAvailable() {
		return true;
	}

	@Override
	public ObjectLoader getSmudgeFiltered(Repository db
			ObjectLoader loader
		if (isAvailable() && isEnabled(db)
				&& (attribute == null || isEnabled(db
				return LfsBlobFilter.smudgeLfsBlob(db
		} else {
			return loader;
		}
	}

	@Override
	public LfsInputStream getCleanFiltered(Repository db
			InputStream input
			throws IOException {
		if (isAvailable() && isEnabled(db
			return new LfsInputStream(LfsBlobFilter.cleanLfsBlob(db
		} else {
			return new LfsInputStream(input
		}
	}

	@Override
	public PrePushHook getPrePushHook(Repository repo
			PrintStream outputStream) {
		if (isAvailable() && isEnabled(repo)) {
			return new LfsPrePushHook(repo
		}
		return null;
	}

	private boolean isEnabled(Repository db) {
		if (db == null) {
			return false;
		}
		return db.getConfig().getBoolean(ConfigConstants.CONFIG_FILTER_SECTION
				Constants.LFS
				false);
	}

	private boolean isEnabled(Repository db
		if (attribute == null) {
			return false;
		}
		return isEnabled(db) && Constants.LFS.equals(attribute.getValue());
	}

}
