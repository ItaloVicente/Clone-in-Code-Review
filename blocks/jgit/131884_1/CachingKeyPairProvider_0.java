package org.eclipse.jgit.transport.sshd;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.ssh.SshTestBase;
import org.eclipse.jgit.transport.sshd.SshdSessionFactory;
import org.eclipse.jgit.util.FS;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ApacheSshTest extends SshTestBase {

	@Override
	protected SshSessionFactory createSessionFactory() {
		SshdSessionFactory result = new SshdSessionFactory(new JGitKeyCache());
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

}
