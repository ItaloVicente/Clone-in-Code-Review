/*
 * Copyright (C) 2018, 2020 Thomas Wolf <thomas.wolf@paranor.ch> and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.eclipse.jgit.junit.ssh;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.CommandFailedException;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SshSupport;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;

/**
 * The ssh tests. Concrete subclasses can re-use these tests by implementing the
 * abstract operations from {@link SshTestHarness}. This gives a way to test
 * different ssh clients against a unified test suite.
 */
public abstract class SshTestBase extends SshTestHarness {

	@DataPoints
			"id_dsa", //
			"id_rsa_1024", //
			"id_rsa_2048", //
			"id_rsa_3072", //
			"id_rsa_4096", //
			"id_ecdsa_256", //
			"id_ecdsa_384", //
			"id_ecdsa_521", //
			"id_ed25519", //
			"id_dsa_testpass", //
			"id_rsa_1024_testpass", //
			"id_rsa_2048_testpass", //
			"id_rsa_3072_testpass", //
			"id_rsa_4096_testpass", //
			"id_ecdsa_256_testpass", //
			"id_ecdsa_384_testpass", //
			"id_ecdsa_521_testpass", //
			"id_ed25519_testpass", //
			"id_ed25519_expensive_testpass" };

	protected File defaultCloneDir;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		defaultCloneDir = new File(getTemporaryDirectory(), "cloned");
	}

	@Test
	public void testSshWithoutConfig() throws Exception {
		assertThrows(TransportException.class,
						+ "/doesntmatter", defaultCloneDir, null));
	}

	@Test
	public void testSingleCommand() throws Exception {
		installConfig("IdentityFile " + privateKey1.getAbsolutePath());
		String command = SshTestGitServer.ECHO_COMMAND + " 1 without timeout";
		long start = System.nanoTime();
		String reply = SshSupport.runSshCommand(
				null, FS.DETECTED, command, 0); // 0 == no timeout
		long elapsed = System.nanoTime() - start;
		assertEquals(command, reply);
		command = SshTestGitServer.ECHO_COMMAND + " 1 expecting no timeout";
		int timeout = 10 * ((int) TimeUnit.NANOSECONDS.toSeconds(elapsed) + 1);
		reply = SshSupport.runSshCommand(
				null, FS.DETECTED, command, timeout);
		assertEquals(command, reply);
	}

	@Test
	public void testSingleCommandWithTimeoutExpired() throws Exception {
		installConfig("IdentityFile " + privateKey1.getAbsolutePath());
		String command = SshTestGitServer.ECHO_COMMAND + " 2 EXPECTING TIMEOUT";

		CommandFailedException e = assertThrows(CommandFailedException.class,
				() -> SshSupport.runSshCommand(new URIish(
						FS.DETECTED, command, 1));
		assertTrue(e.getMessage().contains(command));
		assertTrue(e.getMessage().contains("time"));
	}

	@Test
	public void testSshWithGlobalIdentity() throws Exception {
		cloneWith(
						+ "/doesntmatter",
				defaultCloneDir, null,
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshWithDefaultIdentity() throws Exception {
		File idRsa = new File(privateKey1.getParentFile(), "id_rsa");
		Files.copy(privateKey1.toPath(), idRsa.toPath());
				+ "/doesntmatter", defaultCloneDir, null);
	}

	@Test
	public void testSshWithConfig() throws Exception {
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshWithConfigEncryptedUnusedKey() throws Exception {
		File encryptedKey = new File(sshDir, "id_dsa");
		copyTestResource("id_dsa_testpass", encryptedKey);
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
		assertEquals("CredentialsProvider should not have been called", 0,
				provider.getLog().size());
	}

	@Test
	public void testSshWithConfigEncryptedUnusedKeyInConfigLast()
			throws Exception {
		File encryptedKey = new File(sshDir, "id_dsa_test_key");
		copyTestResource("id_dsa_testpass", encryptedKey);
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(),
				"IdentityFile " + encryptedKey.getAbsolutePath());
		assertEquals("CredentialsProvider should not have been called", 0,
				provider.getLog().size());
	}

	private boolean isJsch() {
		return getSessionFactory().getType().equals("jsch");
	}

	@Test
	public void testSshWithConfigEncryptedUnusedKeyInConfigFirst()
			throws Exception {
		if (isJsch()) {
			return;
		}
		File encryptedKey = new File(sshDir, "id_dsa_test_key");
		copyTestResource("id_dsa_testpass", encryptedKey);
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + encryptedKey.getAbsolutePath(),
				"IdentityFile " + privateKey1.getAbsolutePath());
		assertEquals("CredentialsProvider should have been called once", 1,
				provider.getLog().size());
	}

	@Test
	public void testSshEncryptedUsedKeyCached() throws Exception {
		File encryptedKey = new File(sshDir, "id_dsa_test_key");
		copyTestResource("id_dsa_testpass", encryptedKey);
		File encryptedPublicKey = new File(sshDir, "id_dsa_test_key.pub");
		copyTestResource("id_dsa_testpass.pub", encryptedPublicKey);
		server.setTestUserPublicKey(encryptedPublicKey.toPath());
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
		pushTo(provider,
						defaultCloneDir, provider, //
						"Host localhost", //
						"HostName localhost", //
						"Port " + testPort, //
						"User " + TEST_USER, //
						"IdentityFile " + encryptedKey.getAbsolutePath()));
		assertEquals("CredentialsProvider should have been called once", 1,
				provider.getLog().size());
	}

	@Test(expected = TransportException.class)
	public void testSshEncryptedUsedKeyWrongPassword() throws Exception {
		File encryptedKey = new File(sshDir, "id_dsa_test_key");
		copyTestResource("id_dsa_testpass", encryptedKey);
		File encryptedPublicKey = new File(sshDir, "id_dsa_test_key.pub");
		copyTestResource("id_dsa_testpass.pub", encryptedPublicKey);
		server.setTestUserPublicKey(encryptedPublicKey.toPath());
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass");
				defaultCloneDir, provider, //
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"NumberOfPasswordPrompts 1", //
				"IdentityFile " + encryptedKey.getAbsolutePath());
	}

	@Test
	public void testSshEncryptedUsedKeySeveralPassword() throws Exception {
		File encryptedKey = new File(sshDir, "id_dsa_test_key");
		copyTestResource("id_dsa_testpass", encryptedKey);
		File encryptedPublicKey = new File(sshDir, "id_dsa_test_key.pub");
		copyTestResource("id_dsa_testpass.pub", encryptedPublicKey);
		server.setTestUserPublicKey(encryptedPublicKey.toPath());
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass", "wrongpass2", "testpass");
				defaultCloneDir, provider, //
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + encryptedKey.getAbsolutePath());
		assertEquals("CredentialsProvider should have been called 3 times", 3,
				provider.getLog().size());
	}

	@Test(expected = TransportException.class)
	public void testSshWithoutKnownHosts() throws Exception {
		assertTrue("Could not delete known_hosts", knownHosts.delete());
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshWithoutKnownHostsWithProviderAsk()
			throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile(),
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts",
				knownHosts.renameTo(copiedHosts));
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
		List<LogEntry> messages = provider.getLog();
		assertFalse("Expected user interaction", messages.isEmpty());
		if (isJsch()) {
			assertEquals("Expected to be asked about the key", 1,
					messages.size());
			return;
		}
		assertEquals(
				"Expected to be asked about the key, and the file creation",
				2, messages.size());
		assertTrue("~/.ssh/known_hosts should exist now", knownHosts.exists());
		File clonedAgain = new File(getTemporaryDirectory(), "cloned2");
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshWithoutKnownHostsWithProviderAcceptNew()
			throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile(),
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts",
				knownHosts.renameTo(copiedHosts));
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"StrictHostKeyChecking accept-new", //
				"IdentityFile " + privateKey1.getAbsolutePath());
		if (isJsch()) {
			assertTrue("CredentialsProvider not called",
					provider.getLog().isEmpty());
			return;
		}
		assertEquals("Expected to be asked about the file creation", 1,
				provider.getLog().size());
		assertTrue("~/.ssh/known_hosts should exist now", knownHosts.exists());
		File clonedAgain = new File(getTemporaryDirectory(), "cloned2");
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test(expected = TransportException.class)
	public void testSshWithoutKnownHostsDeny() throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile(),
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts",
				knownHosts.renameTo(copiedHosts));
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"StrictHostKeyChecking yes", //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test(expected = TransportException.class)
	public void testSshModifiedHostKeyDeny()
			throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile(),
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts",
				knownHosts.renameTo(copiedHosts));
		createKnownHostsFile(knownHosts, "localhost", testPort, publicKey1);
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"StrictHostKeyChecking yes", //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test(expected = TransportException.class)
	public void testSshModifiedHostKeyWithProviderDeny() throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile(),
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts",
				knownHosts.renameTo(copiedHosts));
		createKnownHostsFile(knownHosts, "localhost", testPort, publicKey1);
		TestCredentialsProvider provider = new TestCredentialsProvider();
		try {
					"Host localhost", //
					"HostName localhost", //
					"Port " + testPort, //
					"User " + TEST_USER, //
					"StrictHostKeyChecking yes", //
					"IdentityFile " + privateKey1.getAbsolutePath());
		} catch (Exception e) {
			assertEquals("Expected to be told about the modified key", 1,
					provider.getLog().size());
			assertTrue("Only messages expected", provider.getLog().stream()
					.flatMap(l -> l.getItems().stream()).allMatch(
							c -> c instanceof CredentialItem.InformationalMessage));
			throw e;
		}
	}

	private void checkKnownHostsModifiedHostKey(File backup, File newFile,
			String wrongKey) throws IOException {
		List<String> oldLines = Files.readAllLines(backup.toPath(), UTF_8);
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
		assertNotNull("Old key not found", oldKeyPart);
		List<String> newLines = Files.readAllLines(newFile.toPath(), UTF_8);
		assertFalse("Old host key still found in known_hosts file" + newFile,
				hasHostKey("localhost", testPort, wrongKey, newLines));
		assertTrue("New host key not found in known_hosts file" + newFile,
				hasHostKey("localhost", testPort, oldKeyPart, newLines));

	}

	@Test
	public void testSshModifiedHostKeyAllow() throws Exception {
		assertTrue("Failed to delete known_hosts", knownHosts.delete());
		createKnownHostsFile(knownHosts, "localhost", testPort, publicKey1);
		File backup = new File(getTemporaryDirectory(), "backupKnownHosts");
		Files.copy(knownHosts.toPath(), backup.toPath());
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"StrictHostKeyChecking no", //
				"IdentityFile " + privateKey1.getAbsolutePath());
		String[] oldLines = Files
				.readAllLines(backup.toPath(), UTF_8)
				.toArray(new String[0]);
		String[] newLines = Files
				.readAllLines(knownHosts.toPath(), UTF_8)
				.toArray(new String[0]);
		assertArrayEquals("Known hosts file should not be modified", oldLines,
				newLines);
	}

	@Test
	public void testSshModifiedHostKeyAsk() throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile(),
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts",
				knownHosts.renameTo(copiedHosts));
		String wrongKeyPart = createKnownHostsFile(knownHosts, "localhost",
				testPort, publicKey1);
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
		checkKnownHostsModifiedHostKey(copiedHosts, knownHosts, wrongKeyPart);
		assertEquals("Expected to be asked about the modified key", 1,
				provider.getLog().size());
	}

	@Test
	public void testSshCloneWithConfigAndPush() throws Exception {
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath()));
	}

	@Test
	public void testSftpWithConfig() throws Exception {
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSftpCloneWithConfigAndPush() throws Exception {
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath()));
	}

	@Test(expected = TransportException.class)
	public void testSshWithConfigWrongKey() throws Exception {
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey2.getAbsolutePath());
	}

	@Test
	public void testSshWithWrongUserNameInConfig() throws Exception {
		cloneWith(
						+ "/doesntmatter",
				defaultCloneDir, null, //
				"Host localhost", //
				"HostName localhost", //
				"User sombody_else", //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshWithWrongPortInConfig() throws Exception {
		cloneWith(
						+ "/doesntmatter",
				defaultCloneDir, null, //
				"Host localhost", //
				"HostName localhost", //
				"Port 22", //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshWithAliasInConfig() throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), "", //
				"Host localhost", //
				"HostName localhost", //
				"Port 22", //
				"User someone_else", //
				"IdentityFile " + privateKey2.getAbsolutePath());
	}

	@Test
	public void testSshWithUnknownCiphersInConfig() throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), //
				"Ciphers chacha20-poly1305@openssh.com,aes256-gcm@openssh.com,aes128-gcm@openssh.com,aes256-ctr,aes192-ctr,aes128-ctr");
	}

	@Test
	public void testSshWithUnknownHostKeyAlgorithmsInConfig()
			throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), //
				"HostKeyAlgorithms foobar,ssh-rsa,ssh-dss");
	}

	@Test
	public void testSshWithUnknownKexAlgorithmsInConfig()
			throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), //
				"KexAlgorithms foobar,diffie-hellman-group14-sha1,ecdh-sha2-nistp256,ecdh-sha2-nistp384,ecdh-sha2-nistp521");
	}

	@Test
	public void testSshWithMinimalHostKeyAlgorithmsInConfig()
			throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), //
				"HostKeyAlgorithms ssh-rsa,ssh-dss");
	}

	@Test
	public void testSshWithUnknownAuthInConfig() throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), //
				"PreferredAuthentications gssapi-with-mic,hostbased,publickey,keyboard-interactive,password");
	}

	@Test(expected = TransportException.class)
	public void testSshWithNoMatchingAuthInConfig() throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), //
				"PreferredAuthentications password");
	}

	@Test
	public void testRsaHostKeySecond() throws Exception {
		File newHostKey = new File(getTemporaryDirectory(), "newhostkey");
		copyTestResource("id_ecdsa_256", newHostKey);
		server.addHostKey(newHostKey.toPath(), true);
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testEcDsaHostKey() throws Exception {
		File newHostKey = new File(getTemporaryDirectory(), "newhostkey");
		copyTestResource("id_ecdsa_256", newHostKey);
		server.addHostKey(newHostKey.toPath(), false);
		File newHostKeyPub = new File(getTemporaryDirectory(),
				"newhostkey.pub");
		copyTestResource("id_ecdsa_256.pub", newHostKeyPub);
		createKnownHostsFile(knownHosts, "localhost", testPort, newHostKeyPub);
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testPasswordAuth() throws Exception {
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				TEST_USER.toUpperCase(Locale.ROOT));
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications password");
	}

	@Test
	public void testPasswordAuthSeveralTimes() throws Exception {
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass", "wrongpass", TEST_USER.toUpperCase(Locale.ROOT));
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications password");
	}

	@Test(expected = TransportException.class)
	public void testPasswordAuthWrongPassword() throws Exception {
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass");
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications password");
	}

	@Test(expected = TransportException.class)
	public void testPasswordAuthNoPassword() throws Exception {
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications password");
	}

	@Test(expected = TransportException.class)
	public void testPasswordAuthCorrectPasswordTooLate() throws Exception {
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass", "wrongpass", "wrongpass",
				TEST_USER.toUpperCase(Locale.ROOT));
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications password");
	}

	@Test
	public void testKeyboardInteractiveAuth() throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				TEST_USER.toUpperCase(Locale.ROOT));
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications keyboard-interactive");
	}

	@Test
	public void testKeyboardInteractiveAuthSeveralTimes() throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass", "wrongpass", TEST_USER.toUpperCase(Locale.ROOT));
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications keyboard-interactive");
	}

	@Test(expected = TransportException.class)
	public void testKeyboardInteractiveAuthWrongPassword() throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass");
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications keyboard-interactive");
	}

	@Test(expected = TransportException.class)
	public void testKeyboardInteractiveAuthNoPassword() throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications keyboard-interactive");
	}

	@Test(expected = TransportException.class)
	public void testKeyboardInteractiveAuthCorrectPasswordTooLate()
			throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass", "wrongpass", "wrongpass",
				TEST_USER.toUpperCase(Locale.ROOT));
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications keyboard-interactive");
	}

	@Theory
	public void testSshKeys(String keyName) throws Exception {
		assumeTrue(!(isJsch() && (keyName.contains("ed25519")
				|| keyName.startsWith("id_ecdsa_384")
				|| keyName.startsWith("id_ecdsa_521"))));
		File cloned = new File(getTemporaryDirectory(), "cloned");
		String keyFileName = keyName + "_key";
		File privateKey = new File(sshDir, keyFileName);
		copyTestResource(keyName, privateKey);
		File publicKey = new File(sshDir, keyFileName + ".pub");
		copyTestResource(keyName + ".pub", publicKey);
		server.setTestUserPublicKey(publicKey.toPath());
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
		pushTo(provider,
						cloned, provider, //
						"Host localhost", //
						"HostName localhost", //
						"Port " + testPort, //
						"User " + TEST_USER, //
						"IdentityFile " + privateKey.getAbsolutePath()));
		int expectedCalls = keyName.endsWith("testpass") ? 1 : 0;
		assertEquals("Unexpected calls to CredentialsProvider", expectedCalls,
				provider.getLog().size());
		cloned = new File(getTemporaryDirectory(), "cloned2");
		pushTo(null,
						cloned, null, //
						"Host localhost", //
						"HostName localhost", //
						"Port " + testPort, //
						"User " + TEST_USER, //
						"IdentityFile " + privateKey.getAbsolutePath()));
	}
}
