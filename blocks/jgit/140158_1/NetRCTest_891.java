package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.junit.MockSystemReader;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jcraft.jsch.Session;

public class JschConfigSessionFactoryTest {

	File tmpConfigFile;

	OpenSshConfig tmpConfig;

	DefaultSshSessionFactory factory = new DefaultSshSessionFactory();

	@Before
	public void setup() {
		SystemReader.setInstance(new MockSystemReader());
	}

	@After
	public void removeTmpConfig() {
		SystemReader.setInstance(null);
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
		assertEquals(SystemReader.getInstance().getProperty("user.name")
				session.getUserName());
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

	@Test
	public void testAliasCaseDifferenceUpcase() throws Exception {
		tmpConfigFile = createConfig("Host Bitbucket.org"
				"Hostname bitbucket.org"
				"ConnectTimeout 10"
				"Host bitbucket.org"
				"Port 22"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("bitbucket.org"
		assertEquals("foo"
		assertEquals(29418
		assertEquals(TimeUnit.SECONDS.toMillis(10)
	}

	@Test
	public void testAliasCaseDifferenceLowcase() throws Exception {
		tmpConfigFile = createConfig("Host Bitbucket.org"
				"Hostname bitbucket.org"
				"ConnectTimeout 10"
				"Host bitbucket.org"
				"Port 22"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("bitbucket.org"
		assertEquals("bar"
		assertEquals(22
		assertEquals(TimeUnit.SECONDS.toMillis(5)
	}

	@Test
	public void testAliasCaseDifferenceUpcaseInverted() throws Exception {
		tmpConfigFile = createConfig("Host bitbucket.org"
				"Hostname bitbucket.org"
				"ConnectTimeout 5"
				"Host Bitbucket.org"
				"Port 29418"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("bitbucket.org"
		assertEquals("foo"
		assertEquals(29418
		assertEquals(TimeUnit.SECONDS.toMillis(10)
	}

	@Test
	public void testAliasCaseDifferenceLowcaseInverted() throws Exception {
		tmpConfigFile = createConfig("Host bitbucket.org"
				"Hostname bitbucket.org"
				"ConnectTimeout 5"
				"Host Bitbucket.org"
				"Port 29418"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("bitbucket.org"
		assertEquals("bar"
		assertEquals(22
		assertEquals(TimeUnit.SECONDS.toMillis(5)
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
