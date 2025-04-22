
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;

public class NFSFile extends File {
	private static final long serialVersionUID = 1L;

	private final Config config;

	public NFSFile(File parent
		super(parent
		this.config = config;
	}

	public NFSFile(String pathname
		super(pathname);
		this.config = config;
	}

	@Override
	public boolean exists() {
		try {
			refreshFolderStats();
		} catch (IOException e) {
		}
		return super.exists();
	}

	@Override
	public long lastModified() {
		try {
			refreshFolderStats();
		} catch (IOException e) {
		}
		return super.lastModified();
	}

	private void refreshFolderStats() throws IOException {
		boolean refreshFolderStat = config.getBoolean(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_REFRESHFOLDERSTAT
		if (refreshFolderStat) {
			try (DirectoryStream<Path> stream = Files
					.newDirectoryStream(this.toPath().getParent())) {
			}
		}
	}

}
