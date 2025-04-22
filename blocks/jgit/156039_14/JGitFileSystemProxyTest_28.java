package org.eclipse.jgit.niofs.internal;

import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_PORT;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;

public class JGitFileSystemProxyTest extends BaseTest {

	private int gitDaemonPort;

	@Override
	public Map<String
		Map<String
		gitPrefs.put(GIT_DAEMON_ENABLED
		gitDaemonPort = findFreePort();
		gitPrefs.put(GIT_DAEMON_PORT
		return gitPrefs;
	}

	@Test
	public void proxyTest() throws IOException {

		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo

		assertTrue(origin instanceof JGitFileSystemProxy);
		JGitFileSystemProxy proxy = (JGitFileSystemProxy) origin;
		JGitFileSystem realJGitFileSystem = proxy.getRealJGitFileSystem();
		assertTrue(realJGitFileSystem instanceof JGitFileSystemImpl);

		assertTrue(proxy.equals(realJGitFileSystem));
		assertTrue(realJGitFileSystem.equals(proxy));
	}
}
