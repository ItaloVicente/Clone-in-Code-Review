package org.eclipse.jgit.transport.sshd;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.eclipse.jgit.junit.ssh.SshBasicTestBase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.util.FS;

public class ApacheSshProtocol2Test extends SshBasicTestBase {

	@Override
	protected SshSessionFactory createSessionFactory() {
		SshdSessionFactory result = new SshdSessionFactory(new JGitKeyCache()
				null);
		result.setHomeDirectory(FS.DETECTED.userHome());
		result.setSshDirectory(sshDir);
		return result;
	}

	@Override
	protected void installConfig(String... config) {
		File configFile = new File(sshDir
		if (config != null) {
			try {
				Files.write(configFile.toPath()
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		StoredConfig config = ((Repository) db).getConfig();
		config.setInt("protocol"
		config.save();
	}
}
