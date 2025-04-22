
package org.eclipse.jgit.internal.transport.ssh;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.SshConfigStore.HostConfig;
import org.eclipse.jgit.transport.SshConstants;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Before;
import org.junit.Test;

public class OpenSshConfigFileTest extends RepositoryTestCase {

	private File home;

	private File configFile;

	private OpenSshConfigFile osc;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		home = new File(trash
		FileUtils.mkdir(home);

		configFile = new File(new File(home
		FileUtils.mkdir(configFile.getParentFile());

		mockSystemReader.setProperty(Constants.OS_USER_NAME_KEY
		mockSystemReader.setProperty("TST_VAR"
		osc = new OpenSshConfigFile(home
	}

	private void config(String data) throws IOException {
		FS fs = FS.DETECTED;
		long resolution = FS.getFileStoreAttributes(configFile.toPath())
				.getFsTimestampResolution().toNanos();
		Instant lastMtime = fs.lastModifiedInstant(configFile);
		do {
			try (final OutputStreamWriter fw = new OutputStreamWriter(
					new FileOutputStream(configFile)
				fw.write(data);
				TimeUnit.NANOSECONDS.sleep(resolution);
			} catch (InterruptedException e) {
				Thread.interrupted();
			}
		} while (lastMtime.equals(fs.lastModifiedInstant(configFile)));
	}

	private HostConfig lookup(String hostname) {
		return osc.lookupDefault(hostname
	}

	private void assertHost(String expected
		assertEquals(expected
	}

	private void assertUser(String expected
		assertEquals(expected
	}

	private void assertPort(int expected
		assertEquals(expected
				OpenSshConfigFile.positive(h.getValue(SshConstants.PORT)));
	}

	private void assertIdentity(File expected
		String actual = h.getValue(SshConstants.IDENTITY_FILE);
		if (expected == null) {
			assertNull(actual);
		} else {
			assertEquals(expected
		}
	}

	private void assertAttempts(int expected
		assertEquals(expected
				.positive(h.getValue(SshConstants.CONNECTION_ATTEMPTS)));
	}

	@Test
	public void testNoConfig() {
		final HostConfig h = lookup("repo.or.cz");
		assertNotNull(h);
		assertHost("repo.or.cz"
		assertUser("jex_junit"
		assertPort(22
		assertAttempts(1
		assertIdentity(null
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
		assertNotNull(lookup("first"));
		assertHost("first.tld"
		assertNotNull(lookup("second"));
		assertHost("second.tld"
		assertNotNull(lookup("third"));
		assertHost("third.tld"
		assertNotNull(lookup("fourth"));
		assertHost("fourth.tld"
		assertNotNull(lookup("last"));
		assertHost("last.tld"
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
		assertHost("good.tld"
		assertUser("gooduser"
		assertPort(6007
		assertPort(2222
		assertPort(2222
		assertPort(2222
		assertPort(2222
		assertPort(2222
		assertHost(" spaced\ttld "
		assertHost("bad.tld"
	}

	@Test
	public void testAdvancedParsing() throws Exception {
		config("Host foo\n"
				+ " HostName=\"foo\\\"d.tld\"\n"
				+ " User= someone#foo\n"
				+ "Host bar\n"
				+ " User ' some one#two' # Comment\n"
				+ " GlobalKnownHostsFile '/a folder/with spaces/hosts' '/other/more hosts' # Comment\n"
				+ "Host foobar\n"
				+ " User a\\ u\\ thor\n"
				+ "Host backslash\n"
				+ " User some\\one\\\\\\ foo\n"
				+ "Host backslash_before_quote\n"
				+ " User \\\"someone#\"el#se\" #Comment\n"
				+ "Host backslash_in_quote\n"
				+ " User 'some\\one\\\\\\ foo'\n");
		assertHost("foo\"d.tld"
		assertUser("someone#foo"
		HostConfig c = lookup("bar");
		assertUser(" some one#two"
		assertArrayEquals(
				new Object[] { "/a folder/with spaces/hosts"
						"/other/more hosts" }
				c.getValues("GlobalKnownHostsFile").toArray());
		assertUser("a u thor"
		assertUser("some\\one\\ foo"
		assertUser("\"someone#el#se"
		assertUser("some\\one\\\\ foo"
	}

	@Test
	public void testCaseInsensitiveKeyLookup() throws Exception {
		config("Host orcz\n" + "Port 29418\n"
				+ "\tHostName repo.or.cz\nStrictHostKeyChecking yes\n");
		final HostConfig c = lookup("orcz");
		String exactCase = c.getValue("StrictHostKeyChecking");
		assertEquals("yes"
		assertEquals(exactCase
		assertEquals(exactCase
		assertEquals(exactCase
		assertNull(c.getValue("sTrIcThostKEYcheckIN"));
	}

	@Test
	public void testAlias_DoesNotMatch() throws Exception {
		config("Host orcz\n" + "Port 29418\n"
				+ "\tHostName repo.or.cz\n");
		final HostConfig h = lookup("repo.or.cz");
		assertNotNull(h);
		assertHost("repo.or.cz"
		assertUser("jex_junit"
		assertPort(22
		assertIdentity(null
		final HostConfig h2 = lookup("orcz");
		assertHost("repo.or.cz"
		assertUser("jex_junit"
		assertPort(29418
		assertIdentity(null
	}

	@Test
	public void testAlias_OptionsSet() throws Exception {
		config("Host orcz\n" + "\tHostName repo.or.cz\n" + "\tPort 2222\n"
				+ "\tUser jex\n" + "\tIdentityFile .ssh/id_jex\n"
				+ "\tForwardX11 no\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertHost("repo.or.cz"
		assertUser("jex"
		assertPort(2222
		assertIdentity(new File(home
	}

	@Test
	public void testAlias_OptionsKeywordCaseInsensitive() throws Exception {
		config("hOsT orcz\n" + "\thOsTnAmE repo.or.cz\n" + "\tPORT 2222\n"
				+ "\tuser jex\n" + "\tidentityfile .ssh/id_jex\n"
				+ "\tForwardX11 no\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertHost("repo.or.cz"
		assertUser("jex"
		assertPort(2222
		assertIdentity(new File(home
	}

	@Test
	public void testAlias_OptionsInherit() throws Exception {
		config("Host orcz\n" + "\tHostName repo.or.cz\n" + "\n" + "Host *\n"
				+ "\tHostName not.a.host.example.com\n" + "\tPort 2222\n"
				+ "\tUser jex\n" + "\tIdentityFile .ssh/id_jex\n"
				+ "\tForwardX11 no\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertHost("repo.or.cz"
		assertUser("jex"
		assertPort(2222
		assertIdentity(new File(home
	}

	@Test
	public void testAlias_PreferredAuthenticationsDefault() throws Exception {
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertNull(h.getValue(SshConstants.PREFERRED_AUTHENTICATIONS));
	}

	@Test
	public void testAlias_PreferredAuthentications() throws Exception {
		config("Host orcz\n" + "\tPreferredAuthentications publickey\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertEquals("publickey"
				h.getValue(SshConstants.PREFERRED_AUTHENTICATIONS));
	}

	@Test
	public void testAlias_InheritPreferredAuthentications() throws Exception {
		config("Host orcz\n" + "\tHostName repo.or.cz\n" + "\n" + "Host *\n"
				+ "\tPreferredAuthentications 'publickey
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertEquals("publickey
				h.getValue(SshConstants.PREFERRED_AUTHENTICATIONS));
	}

	@Test
	public void testAlias_BatchModeDefault() throws Exception {
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertNull(h.getValue(SshConstants.BATCH_MODE));
	}

	@Test
	public void testAlias_BatchModeYes() throws Exception {
		config("Host orcz\n" + "\tBatchMode yes\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertTrue(OpenSshConfigFile.flag(h.getValue(SshConstants.BATCH_MODE)));
	}

	@Test
	public void testAlias_InheritBatchMode() throws Exception {
		config("Host orcz\n" + "\tHostName repo.or.cz\n" + "\n" + "Host *\n"
				+ "\tBatchMode yes\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertTrue(OpenSshConfigFile.flag(h.getValue(SshConstants.BATCH_MODE)));
	}

	@Test
	public void testAlias_ConnectionAttemptsDefault() throws Exception {
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertAttempts(1
	}

	@Test
	public void testAlias_ConnectionAttempts() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts 5\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertAttempts(5
	}

	@Test
	public void testAlias_invalidConnectionAttempts() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts -1\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertAttempts(1
	}

	@Test
	public void testAlias_badConnectionAttempts() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts xxx\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertAttempts(1
	}

	@Test
	public void testDefaultBlock() throws Exception {
		config("ConnectionAttempts 5\n\nHost orcz\nConnectionAttempts 3\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertAttempts(5
	}

	@Test
	public void testHostCaseInsensitive() throws Exception {
		config("hOsT orcz\nConnectionAttempts 3\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertAttempts(3
	}

	@Test
	public void testListValueSingle() throws Exception {
		config("Host orcz\nUserKnownHostsFile /foo/bar\n");
		final HostConfig c = lookup("orcz");
		assertNotNull(c);
		assertEquals("/foo/bar"
	}

	@Test
	public void testListValueMultiple() throws Exception {
		config("Host orcz\nUserKnownHostsFile \"~/foo/ba z\" /foo/bar \n");
		final HostConfig c = lookup("orcz");
		assertNotNull(c);
		assertArrayEquals(new Object[] { new File(home
				"/foo/bar" }
				c.getValues("UserKnownHostsFile").toArray());
	}

	@Test
	public void testRepeatedLookupsWithModification() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts -1\n");
		final HostConfig h1 = lookup("orcz");
		assertNotNull(h1);
		assertAttempts(1
		config("Host orcz\n" + "\tConnectionAttempts 5\n");
		final HostConfig h2 = lookup("orcz");
		assertNotNull(h2);
		assertNotSame(h1
		assertAttempts(5
		assertAttempts(1
		assertNotSame(h1
	}

	@Test
	public void testIdentityFile() throws Exception {
		config("Host orcz\nIdentityFile \"~/foo/ba z\"\nIdentityFile /foo/bar");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertArrayEquals(new Object[] { new File(home
				"/foo/bar" }
				h.getValues(SshConstants.IDENTITY_FILE).toArray());
	}

	@Test
	public void testMultiIdentityFile() throws Exception {
		config("IdentityFile \"~/foo/ba z\"\nHost orcz\nIdentityFile /foo/bar\nHOST *\nIdentityFile /foo/baz");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertArrayEquals(new Object[] { new File(home
				"/foo/bar"
				h.getValues(SshConstants.IDENTITY_FILE).toArray());
	}

	@Test
	public void testNegatedPattern() throws Exception {
		config("Host repo.or.cz\nIdentityFile ~/foo/bar\nHOST !*.or.cz\nIdentityFile /foo/baz");
		final HostConfig h = lookup("repo.or.cz");
		assertNotNull(h);
		assertIdentity(new File(home
		assertArrayEquals(new Object[] { new File(home
				h.getValues(SshConstants.IDENTITY_FILE).toArray());
	}

	@Test
	public void testPattern() throws Exception {
		config("Host repo.or.cz\nIdentityFile ~/foo/bar\nHOST *.or.cz\nIdentityFile /foo/baz");
		final HostConfig h = lookup("repo.or.cz");
		assertNotNull(h);
		assertIdentity(new File(home
		assertArrayEquals(new Object[] { new File(home
				"/foo/baz" }
				h.getValues(SshConstants.IDENTITY_FILE).toArray());
	}

	@Test
	public void testMultiHost() throws Exception {
		config("Host orcz *.or.cz\nIdentityFile ~/foo/bar\nHOST *.or.cz\nIdentityFile /foo/baz");
		final HostConfig h1 = lookup("repo.or.cz");
		assertNotNull(h1);
		assertIdentity(new File(home
		assertArrayEquals(new Object[] { new File(home
				"/foo/baz" }
				h1.getValues(SshConstants.IDENTITY_FILE).toArray());
		final HostConfig h2 = lookup("orcz");
		assertNotNull(h2);
		assertIdentity(new File(home
		assertArrayEquals(new Object[] { new File(home
				h2.getValues(SshConstants.IDENTITY_FILE).toArray());
	}

	@Test
	public void testEqualsSign() throws Exception {
		config("Host=orcz\n\tConnectionAttempts = 5\n\tUser=\t  foobar\t\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertAttempts(5
		assertUser("foobar"
	}

	@Test
	public void testMissingArgument() throws Exception {
		config("Host=orcz\n\tSendEnv\nIdentityFile\t\nForwardX11\n\tUser=\t  foobar\t\n");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertUser("foobar"
		assertEquals("[]"
		assertIdentity(null
		assertNull(h.getValue("ForwardX11"));
	}

	@Test
	public void testHomeDirUserReplacement() throws Exception {
		config("Host=orcz\n\tIdentityFile %d/.ssh/%u_id_dsa");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertIdentity(new File(new File(home
	}

	@Test
	public void testHostnameReplacement() throws Exception {
		config("Host=orcz\nHost *.*\n\tHostname %h\nHost *\n\tHostname %h.example.org");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertHost("orcz.example.org"
	}

	@Test
	public void testRemoteUserReplacement() throws Exception {
		config("Host=orcz\n\tUser foo\n" + "Host *.*\n\tHostname %h\n"
				+ "Host *\n\tHostname %h.ex%%20ample.org\n\tIdentityFile ~/.ssh/%h_%r_id_dsa");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertIdentity(
				new File(new File(home
						"orcz.ex%20ample.org_foo_id_dsa")
				h);
	}

	@Test
	public void testLocalhostFQDNReplacement() throws Exception {
		String localhost = SystemReader.getInstance().getHostname();
		config("Host=orcz\n\tIdentityFile ~/.ssh/%l_id_dsa");
		final HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertIdentity(
				new File(new File(home
				h);
	}

	@Test
	public void testPubKeyAcceptedAlgorithms() throws Exception {
		config("Host=orcz\n\tPubkeyAcceptedAlgorithms ^ssh-rsa");
		HostConfig h = lookup("orcz");
		assertEquals("^ssh-rsa"
				h.getValue(SshConstants.PUBKEY_ACCEPTED_ALGORITHMS));
		assertEquals("^ssh-rsa"
	}

	@Test
	public void testPubKeyAcceptedKeyTypes() throws Exception {
		config("Host=orcz\n\tPubkeyAcceptedKeyTypes ^ssh-rsa");
		HostConfig h = lookup("orcz");
		assertEquals("^ssh-rsa"
				h.getValue(SshConstants.PUBKEY_ACCEPTED_ALGORITHMS));
		assertEquals("^ssh-rsa"
	}

	@Test
	public void testEolComments() throws Exception {
		config("#Comment\nHost=orcz #Comment\n\tPubkeyAcceptedAlgorithms ^ssh-rsa # Comment\n#Comment");
		HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertEquals("^ssh-rsa"
				h.getValue(SshConstants.PUBKEY_ACCEPTED_ALGORITHMS));
	}

	@Test
	public void testEnVarSubstitution() throws Exception {
		config("Host orcz\nIdentityFile /tmp/${TST_VAR}\n"
				+ "CertificateFile /tmp/${}/foo\nUser ${TST_VAR}\nIdentityAgent /tmp/${TST_VAR/bar");
		HostConfig h = lookup("orcz");
		assertNotNull(h);
		assertEquals("/tmp/TEST"
				h.getValue(SshConstants.IDENTITY_FILE));
		assertEquals("/tmp/${}/foo"
		assertUser("${TST_VAR}"
		assertEquals("/tmp/${TST_VAR/bar"
				h.getValue(SshConstants.IDENTITY_AGENT));
	}

	@Test
	public void testNegativeMatch() throws Exception {
		config("Host foo.bar !foobar.baz *.baz\n" + "Port 29418\n");
		HostConfig h = lookup("foo.bar");
		assertNotNull(h);
		assertPort(29418
		h = lookup("foobar.baz");
		assertNotNull(h);
		assertPort(22
		h = lookup("foo.baz");
		assertNotNull(h);
		assertPort(29418
	}

	@Test
	public void testNegativeMatch2() throws Exception {
		config("Host foo.bar *.baz !foobar.baz\n" + "Port 29418\n");
		HostConfig h = lookup("foo.bar");
		assertNotNull(h);
		assertPort(29418
		h = lookup("foobar.baz");
		assertNotNull(h);
		assertPort(22
		h = lookup("foo.baz");
		assertNotNull(h);
		assertPort(29418
	}

	@Test
	public void testNoMatch() throws Exception {
		config("Host !host1 !host2\n" + "Port 29418\n");
		HostConfig h = lookup("host1");
		assertNotNull(h);
		assertPort(22
		h = lookup("host2");
		assertNotNull(h);
		assertPort(22
		h = lookup("host3");
		assertNotNull(h);
		assertPort(22
	}

	@Test
	public void testMultipleMatch() throws Exception {
		config("Host foo.bar\nPort 29418\nIdentityFile /foo\n\n"
				+ "Host *.bar\nPort 22\nIdentityFile /bar\n"
				+ "Host foo.bar\nPort 47\nIdentityFile /baz\n");
		HostConfig h = lookup("foo.bar");
		assertNotNull(h);
		assertPort(29418
		assertArrayEquals(new Object[] { "/foo"
				h.getValues(SshConstants.IDENTITY_FILE).toArray());
	}

	@Test
	public void testWhitespace() throws Exception {
		config("Host foo \tbar   baz\nPort 29418\n");
		HostConfig h = lookup("foo");
		assertNotNull(h);
		assertPort(29418
		h = lookup("bar");
		assertNotNull(h);
		assertPort(29418
		h = lookup("baz");
		assertNotNull(h);
		assertPort(29418
		h = lookup("\tbar");
		assertNotNull(h);
		assertPort(22
	}
}
