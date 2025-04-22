package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.Callable;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lfs.internal.LfsText;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;

public class InstallLfsCommand implements Callable<Void>{

	@Override
	public Void call() throws Exception {
		StoredConfig cfg = loadUserConfig();

		cfg.setBoolean(ConfigConstants.CONFIG_FILTER_SECTION
				ConfigConstants.CONFIG_KEY_USEJGITBUILTIN
		cfg.setString(ConfigConstants.CONFIG_FILTER_SECTION
				org.eclipse.jgit.lib.Constants.ATTR_FILTER_TYPE_SMUDGE
		cfg.setString(ConfigConstants.CONFIG_FILTER_SECTION
				org.eclipse.jgit.lib.Constants.ATTR_FILTER_TYPE_CLEAN
		cfg.setBoolean(ConfigConstants.CONFIG_FILTER_SECTION
				ConfigConstants.CONFIG_KEY_REQUIRED

		cfg.setString(ConfigConstants.CONFIG_FILTER_SECTION
				"process"

		cfg.save();

		return null;
	}

	private StoredConfig loadUserConfig() throws IOException {
		FileBasedConfig c = SystemReader.getInstance().openUserConfig(null
				FS.DETECTED);

		try {
			c.load();
		} catch (ConfigInvalidException e1) {
			IOException e2 = new IOException(MessageFormat
					.format(LfsText.get().userConfigInvalid
					.getAbsolutePath()
			e2.initCause(e1);
			throw e2;
		}

		return c;
	}

}
