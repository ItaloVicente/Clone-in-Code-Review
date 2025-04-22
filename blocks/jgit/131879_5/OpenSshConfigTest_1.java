package org.eclipse.jgit.transport;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JSchSshTest extends SshTestBase {

	private class TestSshSessionFactory extends JschConfigSessionFactory {

		@Override
		protected void configure(Host hc
		}

		@Override
		public synchronized RemoteSession getSession(URIish uri
				CredentialsProvider credentialsProvider
				throws TransportException {
			return super.getSession(uri
		}

		@Override
		protected JSch createDefaultJSch(FS fs) throws JSchException {
			JSch defaultJSch = super.createDefaultJSch(fs);
			if (knownHosts.exists()) {
				defaultJSch.setKnownHosts(knownHosts.getAbsolutePath());
			}
			return defaultJSch;
		}
	}

	@Override
	protected SshSessionFactory createSessionFactory() {
		return new TestSshSessionFactory();
	}

	@Override
	protected void installConfig(String... config) {
		SshSessionFactory factory = getSessionFactory();
		assertTrue(factory instanceof JschConfigSessionFactory);
		JschConfigSessionFactory j = (JschConfigSessionFactory) factory;
		try {
			j.setConfig(createConfig(config));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private OpenSshConfig createConfig(String... content) throws IOException {
		File configFile = new File(sshDir
		if (content != null) {
			Files.write(configFile.toPath()
		}
		return new OpenSshConfig(getTemporaryDirectory()
	}

}
