package org.eclipse.jgit.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalConfigCache {
	private final static Logger LOG = LoggerFactory
			.getLogger(GlobalConfigCache.class);

	private static AtomicReference<GlobalConfigCache> INSTANCE = new AtomicReference<>(
			new GlobalConfigCache());

	public static GlobalConfigCache getInstance() {
		return INSTANCE.get();
	}

	public static GlobalConfigCache setInstance(FileBasedConfig systemConfig
			FileBasedConfig userConfig) {
		INSTANCE.set(new GlobalConfigCache(systemConfig
		return INSTANCE.get();
	}

	private FileBasedConfig userConfig;

	private FileBasedConfig systemConfig;

	private GlobalConfigCache() {
		FS fs = FS.DETECTED;
		SystemReader sr = SystemReader.getInstance();
		if (StringUtils.isEmptyOrNull(SystemReader.getInstance()
				.getenv(Constants.GIT_CONFIG_NOSYSTEM_KEY))) {
			systemConfig = SystemReader.getInstance().openSystemConfig(null
					fs);
		} else {
			systemConfig = new FileBasedConfig(null
				@Override
				public void load() {
				}

				@Override
				public boolean isOutdated() {
					return false;
				}
			};
		}
		userConfig = sr.openUserConfig(systemConfig
	}

	private GlobalConfigCache(FileBasedConfig systemConfig
			FileBasedConfig userConfig) {
		this.systemConfig = systemConfig;
		this.userConfig = userConfig;
	}

	public StoredConfig getSystemConfig() {
		if (systemConfig.isOutdated()) {
			try {
				LOG.debug("loading system config {}"
						systemConfig);
				systemConfig.load();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (ConfigInvalidException e) {
				throw new RuntimeException(
						MessageFormat.format(
								JGitText.get().systemConfigFileInvalid
								systemConfig.getFile().getAbsolutePath()
						e);
			}
		}
		return systemConfig;
	}

	public StoredConfig getUserConfig() {
		getSystemConfig();
		if (userConfig.isOutdated()) {
			try {
				LOG.debug("loading user config {}"
				userConfig.load();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (ConfigInvalidException e) {
				throw new RuntimeException(MessageFormat.format(
						JGitText.get().userConfigFileInvalid
						userConfig.getFile().getAbsolutePath()
			}
		}
		return userConfig;
	}
}
