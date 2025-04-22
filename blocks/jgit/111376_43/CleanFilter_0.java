package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.hooks.PrePushHook;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.LfsFactory;

public class BuiltinLFS extends LfsFactory {

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
	public ObjectLoader applySmudgeFilter(Repository db
			Attribute attribute) throws IOException {
		if (isEnabled(db) && (attribute == null || isEnabled(db
			return LfsBlobFilter.smudgeLfsBlob(db
		} else {
			return loader;
		}
	}

	@Override
	public LfsInputStream applyCleanFilter(Repository db
			long length
		if (isEnabled(db
			return new LfsInputStream(LfsBlobFilter.cleanLfsBlob(db
		} else {
			return new LfsInputStream(input
		}
	}

	@Override
	public @Nullable PrePushHook getPrePushHook(Repository repo
			PrintStream outputStream) {
		if (isEnabled(repo)) {
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
