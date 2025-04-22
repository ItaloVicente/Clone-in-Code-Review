package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lfs.internal.LfsText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.LfsFactory.LfsInstallCommand;
import org.eclipse.jgit.util.SystemReader;

public class InstallBuiltinLfsCommand implements LfsInstallCommand {

	private static final String[] ARGS_USER = new String[] { "lfs"

	private static final String[] ARGS_LOCAL = new String[] { "lfs"

	private Repository repository;

	@Override
	public Void call() throws Exception {
		StoredConfig cfg = null;
		if (repository == null) {
			cfg = loadUserConfig();
		} else {
			cfg = repository.getConfig();
		}

		cfg.setBoolean(ConfigConstants.CONFIG_FILTER_SECTION
				ConfigConstants.CONFIG_SECTION_LFS
				ConfigConstants.CONFIG_KEY_USEJGITBUILTIN
		cfg.setBoolean(ConfigConstants.CONFIG_FILTER_SECTION
				ConfigConstants.CONFIG_SECTION_LFS
				ConfigConstants.CONFIG_KEY_REQUIRED

		cfg.save();

		ProcessBuilder builder = FS.DETECTED.runInShell("git"
				repository == null ? ARGS_USER : ARGS_LOCAL);
		if (repository != null) {
			builder.directory(repository.isBare() ? repository.getDirectory()
					: repository.getWorkTree());
		}
		FS.DETECTED.runProcess(builder

		return null;
	}

	@Override
	public LfsInstallCommand setRepository(Repository repo) {
		this.repository = repo;
		return this;
	}

	private StoredConfig loadUserConfig() throws IOException {
		FileBasedConfig c = SystemReader.getInstance().openUserConfig(null
				FS.DETECTED);
		try {
			c.load();
		} catch (ConfigInvalidException e1) {
			throw new IOException(MessageFormat
					.format(LfsText.get().userConfigInvalid
							.getAbsolutePath()
					e1);
		}

		return c;
	}

}
