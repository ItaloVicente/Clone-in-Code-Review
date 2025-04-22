package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.util.FS;
import org.junit.After;
import org.junit.Test;

import com.jcraft.jsch.Session;

public class JschConfigSessionFactoryTest {

	File tmpConfigFile;

	OpenSshConfig tmpConfig;

	DefaultSshSessionFactory factory = new DefaultSshSessionFactory();

	@After
	public void removeTmpConfig() {
		if (tmpConfigFile == null) {
			return;
		}
		if (tmpConfigFile.exists() && !tmpConfigFile.delete()) {
			tmpConfigFile.deleteOnExit();
		}
		tmpConfigFile = null;
	}

	@Test
	public void testNoConfigEntry() throws Exception {
		tmpConfigFile = File.createTempFile("jsch"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("egit"
		assertEquals(System.getProperty("user.name")
		assertEquals(22
	}

	@Test
	public void testAlias() throws Exception {
		tmpConfigFile = createConfig("Host egit"
				"User foo"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("git.eclipse.org"
		assertEquals("foo"
		assertEquals(29418
	}

	@Test
	public void testAliasWithUser() throws Exception {
		tmpConfigFile = createConfig("Host egit"
				"User foo"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("git.eclipse.org"
		assertEquals("bar"
		assertEquals(29418
	}

	@Test
	public void testAliasWithPort() throws Exception {
		tmpConfigFile = createConfig("Host egit"
				"User foo"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("git.eclipse.org"
		assertEquals("bar"
		assertEquals(22
	}

	@Test
	public void testAliasIdentical() throws Exception {
		tmpConfigFile = createConfig("Host git.eclipse.org"
				"Hostname git.eclipse.org"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("git.eclipse.org"
		assertEquals("foo"
		assertEquals(29418
	}

	@Test
	public void testAliasIdenticalWithUser() throws Exception {
		tmpConfigFile = createConfig("Host git.eclipse.org"
				"Hostname git.eclipse.org"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("git.eclipse.org"
		assertEquals("bar"
		assertEquals(29418
	}

	@Test
	public void testAliasIdenticalWithPort() throws Exception {
		tmpConfigFile = createConfig("Host git.eclipse.org"
				"Hostname git.eclipse.org"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		Session session = createSession(
		assertEquals("git.eclipse.org"
		assertEquals("bar"
		assertEquals(300
	}

	@Test
	public void testConnectTimout() throws Exception {
		tmpConfigFile = createConfig("Host git.eclipse.org"
				"Hostname git.eclipse.org"
				"ConnectTimeout 10");
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("git.eclipse.org"
		assertEquals("foo"
		assertEquals(29418
		assertEquals(TimeUnit.SECONDS.toMillis(10)
	}

	private File createConfig(String... lines) throws Exception {
		File f = File.createTempFile("jsch"
		Files.write(f.toPath()
		return f;
	}

	private Session createSession(String uriText) throws Exception {
		URIish uri = new URIish(uriText);
		String host = uri.getHost();
		String user = uri.getUser();
		String password = uri.getPass();
		int port = uri.getPort();
		OpenSshConfig.Host hostConfig = tmpConfig.lookup(host);
		host = hostConfig.getHostName();
		if (port <= 0) {
			port = hostConfig.getPort();
		}
		if (user == null) {
			user = hostConfig.getUser();
		}
		return factory.createSession(null
				port
	}
}
