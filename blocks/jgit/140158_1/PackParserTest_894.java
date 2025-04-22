
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Before;
import org.junit.Test;

import com.jcraft.jsch.ConfigRepository;
import com.jcraft.jsch.ConfigRepository.Config;

public class OpenSshConfigTest extends RepositoryTestCase {
	private File home;

	private File configFile;

	private OpenSshConfig osc;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		home = new File(trash
		FileUtils.mkdir(home);

		configFile = new File(new File(home
		FileUtils.mkdir(configFile.getParentFile());

		mockSystemReader.setProperty(Constants.OS_USER_NAME_KEY
		osc = new OpenSshConfig(home
	}

	private void config(String data) throws IOException {
		long lastMtime = configFile.lastModified();
		do {
			try (final OutputStreamWriter fw = new OutputStreamWriter(
					new FileOutputStream(configFile)
				fw.write(data);
			}
		} while (lastMtime == configFile.lastModified());
	}

	@Test
	public void testNoConfig() {
		final Host h = osc.lookup("repo.or.cz");
		assertNotNull(h);
		assertEquals("repo.or.cz"
		assertEquals("jex_junit"
		assertEquals(22
		assertEquals(1
		assertNull(h.getIdentityFile());
	}

	@Test
	public void testSeparatorParsing() throws Exception {
		config("Host\tfirst\n" +
		       "\tHostName\tfirst.tld\n" +
		       "\n" +
		       "Host second\n" +
		       " HostName\tsecond.tld\n" +
		       "Host=third\n" +
		       "HostName=third.tld\n\n\n" +
		       "\t Host = fourth\n\n\n" +
		       " \t HostName\t=fourth.tld\n" +
		       "Host\t =     last\n" +
		       "HostName  \t    last.tld");
		assertNotNull(osc.lookup("first"));
		assertEquals("first.tld"
		assertNotNull(osc.lookup("second"));
		assertEquals("second.tld"
		assertNotNull(osc.lookup("third"));
		assertEquals("third.tld"
		assertNotNull(osc.lookup("fourth"));
		assertEquals("fourth.tld"
		assertNotNull(osc.lookup("last"));
		assertEquals("last.tld"
	}

	@Test
	public void testQuoteParsing() throws Exception {
		config("Host \"good\"\n" +
			" HostName=\"good.tld\"\n" +
			" Port=\"6007\"\n" +
			" User=\"gooduser\"\n" +
			"Host multiple unquoted and \"quoted\" \"hosts\"\n" +
			" Port=\"2222\"\n" +
			"Host \"spaced\"\n" +
			"# Bad host name
			" HostName=\" spaced\ttld \"\n" +
			"# Misbalanced quotes\n" +
			"Host \"bad\"\n" +
			"# OpenSSH doesn't allow this but ...\n" +
			" HostName=bad.tld\"\n");
		assertEquals("good.tld"
		assertEquals("gooduser"
		assertEquals(6007
		assertEquals(2222
		assertEquals(2222
		assertEquals(2222
		assertEquals(2222
		assertEquals(2222
		assertEquals(" spaced\ttld "
		assertEquals("bad.tld\""
	}

	@Test
	public void testCaseInsensitiveKeyLookup() throws Exception {
		config("Host orcz\n" + "Port 29418\n"
				+ "\tHostName repo.or.cz\nStrictHostKeyChecking yes\n");
		final Host h = osc.lookup("orcz");
		Config c = h.getConfig();
		String exactCase = c.getValue("StrictHostKeyChecking");
		assertEquals("yes"
		assertEquals(exactCase
		assertEquals(exactCase
		assertEquals(exactCase
		assertNull(c.getValue("sTrIcThostKEYcheckIN"));
	}

	@Test
	public void testAlias_DoesNotMatch() throws Exception {
		config("Host orcz\n" + "Port 29418\n" + "\tHostName repo.or.cz\n");
		final Host h = osc.lookup("repo.or.cz");
		assertNotNull(h);
		assertEquals("repo.or.cz"
		assertEquals("jex_junit"
		assertEquals(22
		assertNull(h.getIdentityFile());
		final Host h2 = osc.lookup("orcz");
		assertEquals("repo.or.cz"
		assertEquals("jex_junit"
		assertEquals(29418
		assertNull(h.getIdentityFile());
	}

	@Test
	public void testAlias_OptionsSet() throws Exception {
		config("Host orcz\n" + "\tHostName repo.or.cz\n" + "\tPort 2222\n"
				+ "\tUser jex\n" + "\tIdentityFile .ssh/id_jex\n"
				+ "\tForwardX11 no\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals("repo.or.cz"
		assertEquals("jex"
		assertEquals(2222
		assertEquals(new File(home
	}

	@Test
	public void testAlias_OptionsKeywordCaseInsensitive() throws Exception {
		config("hOsT orcz\n" + "\thOsTnAmE repo.or.cz\n" + "\tPORT 2222\n"
				+ "\tuser jex\n" + "\tidentityfile .ssh/id_jex\n"
				+ "\tForwardX11 no\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals("repo.or.cz"
		assertEquals("jex"
		assertEquals(2222
		assertEquals(new File(home
	}

	@Test
	public void testAlias_OptionsInherit() throws Exception {
		config("Host orcz\n" + "\tHostName repo.or.cz\n" + "\n" + "Host *\n"
				+ "\tHostName not.a.host.example.com\n" + "\tPort 2222\n"
				+ "\tUser jex\n" + "\tIdentityFile .ssh/id_jex\n"
				+ "\tForwardX11 no\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals("repo.or.cz"
		assertEquals("jex"
		assertEquals(2222
		assertEquals(new File(home
	}

	@Test
	public void testAlias_PreferredAuthenticationsDefault() throws Exception {
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertNull(h.getPreferredAuthentications());
	}

	@Test
	public void testAlias_PreferredAuthentications() throws Exception {
		config("Host orcz\n" + "\tPreferredAuthentications publickey\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals("publickey"
	}

	@Test
	public void testAlias_InheritPreferredAuthentications() throws Exception {
		config("Host orcz\n" + "\tHostName repo.or.cz\n" + "\n" + "Host *\n"
				+ "\tPreferredAuthentications publickey
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals("publickey
	}

	@Test
	public void testAlias_BatchModeDefault() throws Exception {
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertFalse(h.isBatchMode());
	}

	@Test
	public void testAlias_BatchModeYes() throws Exception {
		config("Host orcz\n" + "\tBatchMode yes\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertTrue(h.isBatchMode());
	}

	@Test
	public void testAlias_InheritBatchMode() throws Exception {
		config("Host orcz\n" + "\tHostName repo.or.cz\n" + "\n" + "Host *\n"
				+ "\tBatchMode yes\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertTrue(h.isBatchMode());
	}

	@Test
	public void testAlias_ConnectionAttemptsDefault() throws Exception {
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(1
	}

	@Test
	public void testAlias_ConnectionAttempts() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts 5\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(5
	}

	@Test
	public void testAlias_invalidConnectionAttempts() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts -1\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(1
	}

	@Test
	public void testAlias_badConnectionAttempts() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts xxx\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(1
	}

	@Test
	public void testDefaultBlock() throws Exception {
		config("ConnectionAttempts 5\n\nHost orcz\nConnectionAttempts 3\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(5
	}

	@Test
	public void testHostCaseInsensitive() throws Exception {
		config("hOsT orcz\nConnectionAttempts 3\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(3
	}

	@Test
	public void testListValueSingle() throws Exception {
		config("Host orcz\nUserKnownHostsFile /foo/bar\n");
		final ConfigRepository.Config c = osc.getConfig("orcz");
		assertNotNull(c);
		assertEquals("/foo/bar"
	}

	@Test
	public void testListValueMultiple() throws Exception {
		config("Host orcz\nUserKnownHostsFile \"~/foo/ba z\" /foo/bar \n");
		final ConfigRepository.Config c = osc.getConfig("orcz");
		assertNotNull(c);
		assertArrayEquals(new Object[] { new File(home
				"/foo/bar" }
				c.getValues("UserKnownHostsFile"));
	}

	@Test
	public void testRepeatedLookupsWithModification() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts -1\n");
		final Host h1 = osc.lookup("orcz");
		assertNotNull(h1);
		assertEquals(1
		config("Host orcz\n" + "\tConnectionAttempts 5\n");
		final Host h2 = osc.lookup("orcz");
		assertNotNull(h2);
		assertNotSame(h1
		assertEquals(5
		assertEquals(1
		assertNotSame(h1.getConfig()
	}

	@Test
	public void testIdentityFile() throws Exception {
		config("Host orcz\nIdentityFile \"~/foo/ba z\"\nIdentityFile /foo/bar");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		File f = h.getIdentityFile();
		assertNotNull(f);
		assertEquals(new File(home
		final ConfigRepository.Config c = h.getConfig();
		assertArrayEquals(new Object[] { new File(home
				"/foo/bar" }
				c.getValues("IdentityFile"));
	}

	@Test
	public void testMultiIdentityFile() throws Exception {
		config("IdentityFile \"~/foo/ba z\"\nHost orcz\nIdentityFile /foo/bar\nHOST *\nIdentityFile /foo/baz");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		File f = h.getIdentityFile();
		assertNotNull(f);
		assertEquals(new File(home
		final ConfigRepository.Config c = h.getConfig();
		assertArrayEquals(new Object[] { new File(home
				"/foo/bar"
				c.getValues("IdentityFile"));
	}

	@Test
	public void testNegatedPattern() throws Exception {
		config("Host repo.or.cz\nIdentityFile ~/foo/bar\nHOST !*.or.cz\nIdentityFile /foo/baz");
		final Host h = osc.lookup("repo.or.cz");
		assertNotNull(h);
		assertEquals(new File(home
		assertArrayEquals(new Object[] { new File(home
				h.getConfig().getValues("IdentityFile"));
	}

	@Test
	public void testPattern() throws Exception {
		config("Host repo.or.cz\nIdentityFile ~/foo/bar\nHOST *.or.cz\nIdentityFile /foo/baz");
		final Host h = osc.lookup("repo.or.cz");
		assertNotNull(h);
		assertEquals(new File(home
		assertArrayEquals(new Object[] { new File(home
				"/foo/baz" }
				h.getConfig().getValues("IdentityFile"));
	}

	@Test
	public void testMultiHost() throws Exception {
		config("Host orcz *.or.cz\nIdentityFile ~/foo/bar\nHOST *.or.cz\nIdentityFile /foo/baz");
		final Host h1 = osc.lookup("repo.or.cz");
		assertNotNull(h1);
		assertEquals(new File(home
		assertArrayEquals(new Object[] { new File(home
				"/foo/baz" }
				h1.getConfig().getValues("IdentityFile"));
		final Host h2 = osc.lookup("orcz");
		assertNotNull(h2);
		assertEquals(new File(home
		assertArrayEquals(new Object[] { new File(home
				h2.getConfig().getValues("IdentityFile"));
	}

	@Test
	public void testEqualsSign() throws Exception {
		config("Host=orcz\n\tConnectionAttempts = 5\n\tUser=\t  foobar\t\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(5
		assertEquals("foobar"
	}

	@Test
	public void testMissingArgument() throws Exception {
		config("Host=orcz\n\tSendEnv\nIdentityFile\t\nForwardX11\n\tUser=\t  foobar\t\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals("foobar"
		assertArrayEquals(new String[0]
		assertNull(h.getIdentityFile());
		assertNull(h.getConfig().getValue("ForwardX11"));
	}

	@Test
	public void testHomeDirUserReplacement() throws Exception {
		config("Host=orcz\n\tIdentityFile %d/.ssh/%u_id_dsa");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(new File(new File(home
				h.getIdentityFile());
	}

	@Test
	public void testHostnameReplacement() throws Exception {
		config("Host=orcz\nHost *.*\n\tHostname %h\nHost *\n\tHostname %h.example.org");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals("orcz.example.org"
	}

	@Test
	public void testRemoteUserReplacement() throws Exception {
		config("Host=orcz\n\tUser foo\n" + "Host *.*\n\tHostname %h\n"
				+ "Host *\n\tHostname %h.ex%%20ample.org\n\tIdentityFile ~/.ssh/%h_%r_id_dsa");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(
				new File(new File(home
						"orcz.ex%20ample.org_foo_id_dsa")
				h.getIdentityFile());
	}

	@Test
	public void testLocalhostFQDNReplacement() throws Exception {
		String localhost = SystemReader.getInstance().getHostname();
		config("Host=orcz\n\tIdentityFile ~/.ssh/%l_id_dsa");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(
				new File(new File(home
				h.getIdentityFile());
	}
}
