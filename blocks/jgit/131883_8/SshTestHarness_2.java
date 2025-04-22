package org.eclipse.jgit.transport.ssh;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.URIish;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;

public abstract class SshTestBase extends SshTestHarness {

	@DataPoints
			"id_dsa"
			"id_rsa_1024"
			"id_rsa_2048"
			"id_rsa_3072"
			"id_rsa_4096"
			"id_ecdsa_256"
			"id_ecdsa_384"
			"id_ecdsa_521"
			"id_dsa_testpass"
			"id_rsa_1024_testpass"
			"id_rsa_2048_testpass"
			"id_rsa_3072_testpass"
			"id_rsa_4096_testpass"
			"id_ecdsa_256_testpass"
			"id_ecdsa_384_testpass"
			"id_ecdsa_521_testpass" };

	protected File defaultCloneDir;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		defaultCloneDir = new File(getTemporaryDirectory()
	}

	@Test(expected = TransportException.class)
	public void testSshWithoutConfig() throws Exception {
				+ "/doesntmatter"
	}

	@Test
	public void testSshWithGlobalIdentity() throws Exception {
		cloneWith(
						+ "/doesntmatter"
				defaultCloneDir
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshWithDefaultIdentity() throws Exception {
		File idRsa = new File(privateKey1.getParentFile()
		Files.copy(privateKey1.toPath()
				+ "/doesntmatter"
	}

	@Test
	public void testSshWithConfig() throws Exception {
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshWithConfigEncryptedUnusedKey() throws Exception {
		File encryptedKey = new File(sshDir
		copyTestResource("id_dsa_testpass"
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
		assertEquals("CredentialsProvider should not have been called"
				provider.getLog().size());
	}

	@Test
	public void testSshWithConfigEncryptedUnusedKeyInConfigLast()
			throws Exception {
		File encryptedKey = new File(sshDir
		copyTestResource("id_dsa_testpass"
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath()
				"IdentityFile " + encryptedKey.getAbsolutePath());
		assertEquals("CredentialsProvider should not have been called"
				provider.getLog().size());
	}

	@Test
	public void testSshWithConfigEncryptedUnusedKeyInConfigFirst()
			throws Exception {
		assumeTrue(!(getSessionFactory() instanceof JschConfigSessionFactory));
		File encryptedKey = new File(sshDir
		copyTestResource("id_dsa_testpass"
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + encryptedKey.getAbsolutePath()
				"IdentityFile " + privateKey1.getAbsolutePath());
		assertEquals("CredentialsProvider should have been called once"
				provider.getLog().size());
	}

	@Test
	public void testSshEncryptedUsedKeyCached() throws Exception {
		File encryptedKey = new File(sshDir
		copyTestResource("id_dsa_testpass"
		File encryptedPublicKey = new File(sshDir
		copyTestResource("id_dsa_testpass.pub"
		server.setTestUserPublicKey(encryptedPublicKey.toPath());
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
		pushTo(provider
						defaultCloneDir
						"Host localhost"
						"HostName localhost"
						"Port " + testPort
						"User " + TEST_USER
						"IdentityFile " + encryptedKey.getAbsolutePath()));
		assertEquals("CredentialsProvider should have been called once"
				provider.getLog().size());
	}

	@Test(expected = TransportException.class)
	public void testSshWithoutKnownHosts() throws Exception {
		assertTrue("Could not delete known_hosts"
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshWithoutKnownHostsWithProviderAsk()
			throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile()
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts"
				knownHosts.renameTo(copiedHosts));
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
		Map<URIish
		assertFalse("Expected user interaction"
		if (getSessionFactory() instanceof JschConfigSessionFactory) {
			assertEquals("Expected to be asked about the key"
					messages.size());
			return;
		}
		assertEquals(
				"Expected to be asked about the key
				2
		assertTrue("~/.ssh/known_hosts should exist now"
		File clonedAgain = new File(getTemporaryDirectory()
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshWithoutKnownHostsWithProviderAcceptNew()
			throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile()
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts"
				knownHosts.renameTo(copiedHosts));
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"StrictHostKeyChecking accept-new"
				"IdentityFile " + privateKey1.getAbsolutePath());
		if (getSessionFactory() instanceof JschConfigSessionFactory) {
			assertTrue("CredentialsProvider not called"
					provider.getLog().isEmpty());
			return;
		}
		assertEquals("Expected to be asked about the file creation"
				provider.getLog().size());
		assertTrue("~/.ssh/known_hosts should exist now"
		File clonedAgain = new File(getTemporaryDirectory()
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test(expected = TransportException.class)
	public void testSshWithoutKnownHostsDeny() throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile()
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts"
				knownHosts.renameTo(copiedHosts));
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"StrictHostKeyChecking yes"
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test(expected = TransportException.class)
	public void testSshModifiedHostKeyDeny()
			throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile()
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts"
				knownHosts.renameTo(copiedHosts));
		createKnownHostsFile(knownHosts
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"StrictHostKeyChecking yes"
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test(expected = TransportException.class)
	public void testSshModifiedHostKeyWithProviderDeny() throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile()
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts"
				knownHosts.renameTo(copiedHosts));
		createKnownHostsFile(knownHosts
		TestCredentialsProvider provider = new TestCredentialsProvider();
		try {
					"Host localhost"
					"HostName localhost"
					"Port " + testPort
					"User " + TEST_USER
					"StrictHostKeyChecking yes"
					"IdentityFile " + privateKey1.getAbsolutePath());
		} catch (Exception e) {
			assertEquals("Expected to be told about the modified key"
					provider.getLog().size());
			assertTrue("Only messages expected"
					.stream().flatMap(List::stream).allMatch(
							c -> c instanceof CredentialItem.InformationalMessage));
			throw e;
		}
	}

	private void checkKnownHostsModifiedHostKey(File backup
			String wrongKey) throws IOException {
		List<String> oldLines = Files.readAllLines(backup.toPath()
				StandardCharsets.UTF_8);
		String oldKeyPart = null;
		for (String oldLine : oldLines) {
			if (oldLine.contains("[localhost]:")) {
				String[] parts = oldLine.split("\\s+");
				if (parts.length > 2) {
					oldKeyPart = parts[parts.length - 2] + ' '
							+ parts[parts.length - 1];
					break;
				}
			}
		}
		assertNotNull("Old key not found"
		List<String> newLines = Files.readAllLines(newFile.toPath()
				StandardCharsets.UTF_8);
		assertFalse("Old host key still found in known_hosts file" + newFile
				hasHostKey("localhost"
		assertTrue("New host key not found in known_hosts file" + newFile
				hasHostKey("localhost"

	}

	@Test
	public void testSshModifiedHostKeyAllow() throws Exception {
		assertTrue("Failed to delete known_hosts"
		createKnownHostsFile(knownHosts
		File backup = new File(getTemporaryDirectory()
		Files.copy(knownHosts.toPath()
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"StrictHostKeyChecking no"
				"IdentityFile " + privateKey1.getAbsolutePath());
		String[] oldLines = Files
				.readAllLines(backup.toPath()
				.toArray(new String[0]);
		String[] newLines = Files
				.readAllLines(knownHosts.toPath()
				.toArray(new String[0]);
		assertArrayEquals("Known hosts file should not be modified"
				newLines);
	}

	@Test
	public void testSshModifiedHostKeyAsk() throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile()
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts"
				knownHosts.renameTo(copiedHosts));
		String wrongKeyPart = createKnownHostsFile(knownHosts
				testPort
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
		checkKnownHostsModifiedHostKey(copiedHosts
		assertEquals("Expected to be asked about the modified key"
				provider.getLog().size());
	}

	@Test
	public void testSshCloneWithConfigAndPush() throws Exception {
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath()));
	}

	@Test
	public void testSftpWithConfig() throws Exception {
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSftpCloneWithConfigAndPush() throws Exception {
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath()));
	}

	@Test(expected = TransportException.class)
	public void testSshWithConfigWrongKey() throws Exception {
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey2.getAbsolutePath());
	}

	@Test
	public void testSshWithWrongUserNameInConfig() throws Exception {
		cloneWith(
						+ "/doesntmatter"
				defaultCloneDir
				"Host localhost"
				"HostName localhost"
				"User sombody_else"
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshWithWrongPortInConfig() throws Exception {
		cloneWith(
						+ "/doesntmatter"
				defaultCloneDir
				"Host localhost"
				"HostName localhost"
				"Port 22"
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshWithAliasInConfig() throws Exception {
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath()
				"Host localhost"
				"HostName localhost"
				"Port 22"
				"User someone_else"
				"IdentityFile " + privateKey2.getAbsolutePath());
	}

	@Test
	public void testSshWithUnknownCiphersInConfig() throws Exception {
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath()
				"Ciphers chacha20-poly1305@openssh.com
	}

	@Test
	public void testSshWithUnknownHostKeyAlgorithmsInConfig()
			throws Exception {
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath()
				"HostKeyAlgorithms foobar
	}

	@Test
	public void testSshWithUnknownKexAlgorithmsInConfig()
			throws Exception {
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath()
				"KexAlgorithms foobar
	}

	@Test
	public void testSshWithMinimalHostKeyAlgorithmsInConfig()
			throws Exception {
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath()
				"HostKeyAlgorithms ssh-rsa
	}

	@Theory
	public void testSshKeys(String keyName) throws Exception {
		assumeTrue(!(getSessionFactory() instanceof JschConfigSessionFactory
				&& (keyName.startsWith("id_ecdsa_384")
						|| keyName.startsWith("id_ecdsa_521"))));
		File cloned = new File(getTemporaryDirectory()
		String keyFileName = keyName + "_key";
		File privateKey = new File(sshDir
		copyTestResource(keyName
		File publicKey = new File(sshDir
		copyTestResource(keyName + ".pub"
		server.setTestUserPublicKey(publicKey.toPath());
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
		pushTo(provider
						cloned
						"Host localhost"
						"HostName localhost"
						"Port " + testPort
						"User " + TEST_USER
						"IdentityFile " + privateKey.getAbsolutePath()));
		int expectedCalls = keyName.endsWith("testpass") ? 1 : 0;
		assertEquals("Unexpected calls to CredentialsProvider"
				provider.getLog().size());
		cloned = new File(getTemporaryDirectory()
		pushTo(null
						cloned
						"Host localhost"
						"HostName localhost"
						"Port " + testPort
						"User " + TEST_USER
						"IdentityFile " + privateKey.getAbsolutePath()));
	}
}
