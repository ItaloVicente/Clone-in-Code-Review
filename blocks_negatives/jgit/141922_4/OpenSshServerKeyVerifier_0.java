/*
 * Copyright (C) 2018, Thomas Wolf <thomas.wolf@paranor.ch>
 * and other copyright owners as documented in the project's IP log.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Distribution License v1.0 which
 * accompanies this distribution, is reproduced below, and is
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name of the Eclipse Foundation, Inc. nor the
 *   names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.eclipse.jgit.internal.transport.sshd;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.text.MessageFormat.format;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.apache.sshd.client.config.hosts.KnownHostEntry;
import org.apache.sshd.client.keyverifier.KnownHostsServerKeyVerifier;
import org.apache.sshd.client.keyverifier.KnownHostsServerKeyVerifier.HostEntryPair;
import org.apache.sshd.client.keyverifier.ServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.config.keys.AuthorizedKeyEntry;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.config.keys.PublicKeyEntryResolver;
import org.apache.sshd.common.digest.BuiltinDigests;
import org.apache.sshd.common.util.io.ModifiableFileWatcher;
import org.apache.sshd.common.util.net.SshdSocketAddress;
import org.eclipse.jgit.internal.storage.file.LockFile;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.SshConstants;
import org.eclipse.jgit.transport.URIish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A sever host key verifier that honors the {@code StrictHostKeyChecking} and
 * {@code UserKnownHostsFile} values from the ssh configuration.
 * <p>
 * The verifier can be given default known_hosts files in the constructor, which
 * will be used if the ssh config does not specify a {@code UserKnownHostsFile}.
 * If the ssh config <em>does</em> set {@code UserKnownHostsFile}, the verifier
 * uses the given files in the order given. Non-existing or unreadable files are
 * ignored.
 * <p>
 * {@code StrictHostKeyChecking} accepts the following values:
 * </p>
 * <dl>
 * <dt>ask</dt>
 * <dd>Ask the user whether new or changed keys shall be accepted and be added
 * to the known_hosts file.</dd>
 * <dt>yes/true</dt>
 * <dd>Accept only keys listed in the known_hosts file.</dd>
 * <dt>no/false</dt>
 * <dd>Silently accept all new or changed keys, add new keys to the known_hosts
 * file.</dd>
 * <dt>accept-new</dt>
 * <dd>Silently accept keys for new hosts and add them to the known_hosts
 * file.</dd>
 * </dl>
 * <p>
 * If {@code StrictHostKeyChecking} is not set, or set to any other value, the
 * default value <b>ask</b> is active.
 * </p>
 * <p>
 * This implementation relies on the {@link ClientSession} being a
 * {@link JGitClientSession}. By default Apache MINA sshd does not forward the
 * config file host entry to the session, so it would be unknown here which
 * entry it was and what setting of {@code StrictHostKeyChecking} should be
 * used. If used with some other session type, the implementation assumes
 * "<b>ask</b>".
 * <p>
 * <p>
 * Asking the user is done via a {@link CredentialsProvider} obtained from the
 * session. If none is set, the implementation falls back to strict host key
 * checking ("<b>yes</b>").
 * </p>
 * <p>
 * Note that adding a key to the known hosts file may create the file. You can
 * specify in the constructor whether the user shall be asked about that, too.
 * If the user declines updating the file, but the key was otherwise
 * accepted (user confirmed for "<b>ask</b>", or "no" or "accept-new" are
 * active), the key is accepted for this session only.
 * </p>
 * <p>
 * If several known hosts files are specified, a new key is always added to the
 * first file (even if it doesn't exist yet; see the note about file creation
 * above).
 * </p>
 *
 *      ssh-config</a>
 */
public class OpenSshServerKeyVerifier
		implements ServerKeyVerifier, ServerKeyLookup {


	private static final Logger LOG = LoggerFactory
			.getLogger(OpenSshServerKeyVerifier.class);

	/** Can be used to mark revoked known host lines. */

	private final boolean askAboutNewFile;

	private final Map<Path, HostKeyFile> knownHostsFiles = new ConcurrentHashMap<>();

	private final List<HostKeyFile> defaultFiles = new ArrayList<>();

	private enum ModifiedKeyHandling {
		DENY, ALLOW, ALLOW_AND_STORE
	}

	/**
	 * Creates a new {@link OpenSshServerKeyVerifier}.
	 *
	 * @param askAboutNewFile
	 *            whether to ask the user, if possible, about creating a new
	 *            non-existing known_hosts file
	 * @param defaultFiles
	 *            typically ~/.ssh/known_hosts and ~/.ssh/known_hosts2. May be
	 *            empty or {@code null}, in which case no default files are
	 *            installed. The files need not exist.
	 */
	public OpenSshServerKeyVerifier(boolean askAboutNewFile,
			List<Path> defaultFiles) {
		if (defaultFiles != null) {
			for (Path file : defaultFiles) {
				HostKeyFile newFile = new HostKeyFile(file);
				knownHostsFiles.put(file, newFile);
				this.defaultFiles.add(newFile);
			}
		}
		this.askAboutNewFile = askAboutNewFile;
	}

	private List<HostKeyFile> getFilesToUse(ClientSession session) {
		List<HostKeyFile> filesToUse = defaultFiles;
		if (session instanceof JGitClientSession) {
			HostConfigEntry entry = ((JGitClientSession) session)
					.getHostConfigEntry();
			if (entry instanceof JGitHostConfigEntry) {
				List<HostKeyFile> userFiles = addUserHostKeyFiles(
						((JGitHostConfigEntry) entry).getMultiValuedOptions()
								.get(SshConstants.USER_KNOWN_HOSTS_FILE));
				if (!userFiles.isEmpty()) {
					filesToUse = userFiles;
				}
			}
		}
		return filesToUse;
	}

	@Override
	public List<HostEntryPair> lookup(ClientSession session,
			SocketAddress remote) {
		List<HostKeyFile> filesToUse = getFilesToUse(session);
		HostKeyHelper helper = new HostKeyHelper();
		List<HostEntryPair> result = new ArrayList<>();
		Collection<SshdSocketAddress> candidates = helper
				.resolveHostNetworkIdentities(session, remote);
		for (HostKeyFile file : filesToUse) {
			for (HostEntryPair current : file.get()) {
				KnownHostEntry entry = current.getHostEntry();
				for (SshdSocketAddress host : candidates) {
					if (entry.isHostMatch(host.getHostName(), host.getPort())) {
						result.add(current);
						break;
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean verifyServerKey(ClientSession clientSession,
			SocketAddress remoteAddress, PublicKey serverKey) {
		List<HostKeyFile> filesToUse = getFilesToUse(clientSession);
		AskUser ask = new AskUser();
		HostEntryPair[] modified = { null };
		Path path = null;
		HostKeyHelper helper = new HostKeyHelper();
		for (HostKeyFile file : filesToUse) {
			try {
				if (find(clientSession, remoteAddress, serverKey, file.get(),
						modified, helper)) {
					return true;
				}
			} catch (RevokedKeyException e) {
				ask.revokedKey(clientSession, remoteAddress, serverKey,
						file.getPath());
				return false;
			}
			if (path == null && modified[0] != null) {
				path = file.getPath();
			}
		}
		if (modified[0] != null) {
			ModifiedKeyHandling toDo = ask.acceptModifiedServerKey(
					clientSession, remoteAddress, modified[0].getServerKey(),
					serverKey, path);
			if (toDo == ModifiedKeyHandling.ALLOW_AND_STORE) {
				try {
					updateModifiedServerKey(clientSession, remoteAddress,
							serverKey, modified[0], path, helper);
					knownHostsFiles.get(path).resetReloadAttributes();
				} catch (IOException e) {
					LOG.warn(format(SshdText.get().knownHostsCouldNotUpdate,
							path));
				}
			}
			if (toDo == ModifiedKeyHandling.DENY) {
				return false;
			}
			return true;
		} else if (ask.acceptUnknownKey(clientSession, remoteAddress,
				serverKey)) {
			if (!filesToUse.isEmpty()) {
				HostKeyFile toUpdate = filesToUse.get(0);
				path = toUpdate.getPath();
				try {
					updateKnownHostsFile(clientSession, remoteAddress,
							serverKey, path, helper);
					toUpdate.resetReloadAttributes();
				} catch (IOException e) {
					LOG.warn(format(SshdText.get().knownHostsCouldNotUpdate,
							path));
				}
			}
			return true;
		}
		return false;
	}

	private static class RevokedKeyException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	private boolean find(ClientSession clientSession,
			SocketAddress remoteAddress, PublicKey serverKey,
			List<HostEntryPair> entries, HostEntryPair[] modified,
			HostKeyHelper helper) throws RevokedKeyException {
		Collection<SshdSocketAddress> candidates = helper
				.resolveHostNetworkIdentities(clientSession, remoteAddress);
		for (HostEntryPair current : entries) {
			KnownHostEntry entry = current.getHostEntry();
			for (SshdSocketAddress host : candidates) {
				if (entry.isHostMatch(host.getHostName(), host.getPort())) {
					boolean isRevoked = MARKER_REVOKED
							.equals(entry.getMarker());
					if (KeyUtils.compareKeys(serverKey,
							current.getServerKey())) {
						if (isRevoked) {
							throw new RevokedKeyException();
						}
						modified[0] = null;
						return true;
					} else if (!isRevoked) {
						modified[0] = current;
					}
				}
			}
		}
		return false;
	}

	private List<HostKeyFile> addUserHostKeyFiles(List<String> fileNames) {
		if (fileNames == null || fileNames.isEmpty()) {
			return Collections.emptyList();
		}
		List<HostKeyFile> userFiles = new ArrayList<>();
		for (String name : fileNames) {
			try {
				Path path = Paths.get(name);
				HostKeyFile file = knownHostsFiles.computeIfAbsent(path,
						p -> new HostKeyFile(path));
				userFiles.add(file);
			} catch (InvalidPathException e) {
				LOG.warn(format(SshdText.get().knownHostsInvalidPath,
						name));
			}
		}
		return userFiles;
	}

	private void updateKnownHostsFile(ClientSession clientSession,
			SocketAddress remoteAddress, PublicKey serverKey, Path path,
			HostKeyHelper updater)
			throws IOException {
		KnownHostEntry entry = updater.prepareKnownHostEntry(clientSession,
				remoteAddress, serverKey);
		if (entry == null) {
			return;
		}
		if (!Files.exists(path)) {
			if (askAboutNewFile) {
				CredentialsProvider provider = getCredentialsProvider(
						clientSession);
				if (provider == null) {
					return;
				}
				URIish uri = new URIish().setPath(path.toString());
				if (!askUser(provider, uri, //
						format(SshdText.get().knownHostsUserAskCreationPrompt,
								path), //
						format(SshdText.get().knownHostsUserAskCreationMsg,
								path))) {
					return;
				}
			}
		}
		LockFile lock = new LockFile(path.toFile());
		if (lock.lockForAppend()) {
			try {
				try (BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(lock.getOutputStream(),
								UTF_8))) {
					writer.newLine();
					writer.write(entry.getConfigLine());
					writer.newLine();
				}
				lock.commit();
			} catch (IOException e) {
				lock.unlock();
				throw e;
			}
		} else {
			LOG.warn(format(SshdText.get().knownHostsFileLockedUpdate,
					path));
		}
	}

	private void updateModifiedServerKey(ClientSession clientSession,
			SocketAddress remoteAddress, PublicKey serverKey,
			HostEntryPair entry, Path path, HostKeyHelper helper)
			throws IOException {
		KnownHostEntry hostEntry = entry.getHostEntry();
		String oldLine = hostEntry.getConfigLine();
		String newLine = helper.prepareModifiedServerKeyLine(clientSession,
				remoteAddress, hostEntry, oldLine, entry.getServerKey(),
				serverKey);
		if (newLine == null || newLine.isEmpty()) {
			return;
		}
		if (oldLine == null || oldLine.isEmpty() || newLine.equals(oldLine)) {
			return;
		}
		LockFile lock = new LockFile(path.toFile());
		if (lock.lock()) {
			try {
				try (BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(lock.getOutputStream(), UTF_8));
						BufferedReader reader = Files.newBufferedReader(path,
								UTF_8)) {
					boolean done = false;
					String line;
					while ((line = reader.readLine()) != null) {
						String toWrite = line;
						if (!done) {
							int pos = line.indexOf('#');
							String toTest = pos < 0 ? line
									: line.substring(0, pos);
							if (toTest.trim().equals(oldLine)) {
								toWrite = newLine;
								done = true;
							}
						}
						writer.write(toWrite);
						writer.newLine();
					}
				}
				lock.commit();
			} catch (IOException e) {
				lock.unlock();
				throw e;
			}
		} else {
			LOG.warn(format(SshdText.get().knownHostsFileLockedUpdate,
					path));
		}
	}

	private static CredentialsProvider getCredentialsProvider(
			ClientSession session) {
		if (session instanceof JGitClientSession) {
			return ((JGitClientSession) session).getCredentialsProvider();
		}
		return null;
	}

	private static boolean askUser(CredentialsProvider provider, URIish uri,
			String prompt, String... messages) {
		List<CredentialItem> items = new ArrayList<>(messages.length + 1);
		for (String message : messages) {
			items.add(new CredentialItem.InformationalMessage(message));
		}
		if (prompt != null) {
			CredentialItem.YesNoType answer = new CredentialItem.YesNoType(
					prompt);
			items.add(answer);
			return provider.get(uri, items) && answer.getValue();
		} else {
			return provider.get(uri, items);
		}
	}

	private static class AskUser {

		private enum Check {
			ASK, DENY, ALLOW;
		}

		@SuppressWarnings("nls")
		private Check checkMode(ClientSession session,
				SocketAddress remoteAddress, boolean changed) {
			if (!(remoteAddress instanceof InetSocketAddress)) {
				return Check.DENY;
			}
			if (session instanceof JGitClientSession) {
				HostConfigEntry entry = ((JGitClientSession) session)
						.getHostConfigEntry();
				String value = entry.getProperty(
						SshConstants.STRICT_HOST_KEY_CHECKING, "ask");
				switch (value.toLowerCase(Locale.ROOT)) {
				case SshConstants.YES:
				case SshConstants.ON:
					return Check.DENY;
				case SshConstants.NO:
				case SshConstants.OFF:
					return Check.ALLOW;
				case "accept-new":
					return changed ? Check.DENY : Check.ALLOW;
				default:
					break;
				}
			}
			if (getCredentialsProvider(session) == null) {
				return Check.DENY;
			}
			return Check.ASK;
		}

		public void revokedKey(ClientSession clientSession,
				SocketAddress remoteAddress, PublicKey serverKey, Path path) {
			CredentialsProvider provider = getCredentialsProvider(
					clientSession);
			if (provider == null) {
				return;
			}
			InetSocketAddress remote = (InetSocketAddress) remoteAddress;
			URIish uri = JGitUserInteraction.toURI(clientSession.getUsername(),
					remote);
			String sha256 = KeyUtils.getFingerPrint(BuiltinDigests.sha256,
					serverKey);
			String md5 = KeyUtils.getFingerPrint(BuiltinDigests.md5, serverKey);
			String keyAlgorithm = serverKey.getAlgorithm();
			askUser(provider, uri, null, //
					format(SshdText.get().knownHostsRevokedKeyMsg,
							remote.getHostString(), path),
					format(SshdText.get().knownHostsKeyFingerprints,
							keyAlgorithm),
					md5, sha256);
		}

		public boolean acceptUnknownKey(ClientSession clientSession,
				SocketAddress remoteAddress, PublicKey serverKey) {
			Check check = checkMode(clientSession, remoteAddress, false);
			if (check != Check.ASK) {
				return check == Check.ALLOW;
			}
			CredentialsProvider provider = getCredentialsProvider(
					clientSession);
			InetSocketAddress remote = (InetSocketAddress) remoteAddress;
			String sha256 = KeyUtils.getFingerPrint(BuiltinDigests.sha256,
					serverKey);
			String md5 = KeyUtils.getFingerPrint(BuiltinDigests.md5, serverKey);
			String keyAlgorithm = serverKey.getAlgorithm();
			String remoteHost = remote.getHostString();
			URIish uri = JGitUserInteraction.toURI(clientSession.getUsername(),
					remote);
			String prompt = SshdText.get().knownHostsUnknownKeyPrompt;
			return askUser(provider, uri, prompt, //
					format(SshdText.get().knownHostsUnknownKeyMsg,
							remoteHost),
					format(SshdText.get().knownHostsKeyFingerprints,
							keyAlgorithm),
					md5, sha256);
		}

		public ModifiedKeyHandling acceptModifiedServerKey(
				ClientSession clientSession,
				SocketAddress remoteAddress, PublicKey expected,
				PublicKey actual, Path path) {
			Check check = checkMode(clientSession, remoteAddress, true);
			if (check == Check.ALLOW) {
				return ModifiedKeyHandling.ALLOW;
			}
			InetSocketAddress remote = (InetSocketAddress) remoteAddress;
			String keyAlgorithm = actual.getAlgorithm();
			String remoteHost = remote.getHostString();
			URIish uri = JGitUserInteraction.toURI(clientSession.getUsername(),
					remote);
			List<String> messages = new ArrayList<>();
			String warning = format(
					SshdText.get().knownHostsModifiedKeyWarning,
					keyAlgorithm, expected.getAlgorithm(), remoteHost,
					KeyUtils.getFingerPrint(BuiltinDigests.md5, expected),
					KeyUtils.getFingerPrint(BuiltinDigests.sha256, expected),
					KeyUtils.getFingerPrint(BuiltinDigests.md5, actual),
					KeyUtils.getFingerPrint(BuiltinDigests.sha256, actual));

			CredentialsProvider provider = getCredentialsProvider(
					clientSession);
			if (check == Check.DENY) {
				if (provider != null) {
					messages.add(format(
							SshdText.get().knownHostsModifiedKeyDenyMsg, path));
					askUser(provider, uri, null,
							messages.toArray(new String[0]));
				}
				return ModifiedKeyHandling.DENY;
			}
			List<CredentialItem> items = new ArrayList<>(messages.size() + 2);
			for (String message : messages) {
				items.add(new CredentialItem.InformationalMessage(message));
			}
			CredentialItem.YesNoType proceed = new CredentialItem.YesNoType(
					SshdText.get().knownHostsModifiedKeyAcceptPrompt);
			CredentialItem.YesNoType store = new CredentialItem.YesNoType(
					SshdText.get().knownHostsModifiedKeyStorePrompt);
			items.add(proceed);
			items.add(store);
			if (provider.get(uri, items) && proceed.getValue()) {
				return store.getValue() ? ModifiedKeyHandling.ALLOW_AND_STORE
						: ModifiedKeyHandling.ALLOW;
			}
			return ModifiedKeyHandling.DENY;
		}

	}

	private static class HostKeyFile extends ModifiableFileWatcher
			implements Supplier<List<HostEntryPair>> {

		private List<HostEntryPair> entries = Collections.emptyList();

		public HostKeyFile(Path path) {
			super(path);
		}

		@Override
		public List<HostEntryPair> get() {
			Path path = getPath();
			try {
				if (checkReloadRequired()) {
					if (!Files.exists(path)) {
						resetReloadAttributes();
						return Collections.emptyList();
					}
					LockFile lock = new LockFile(path.toFile());
					if (lock.lock()) {
						try {
							entries = reload(getPath());
						} finally {
							lock.unlock();
						}
					} else {
						LOG.warn(format(SshdText.get().knownHostsFileLockedRead,
								path));
					}
				}
			} catch (IOException e) {
				LOG.warn(format(SshdText.get().knownHostsFileReadFailed, path));
			}
			return Collections.unmodifiableList(entries);
		}

		private List<HostEntryPair> reload(Path path) throws IOException {
			try {
				List<KnownHostEntry> rawEntries = KnownHostEntryReader
						.readFromFile(path);
				updateReloadAttributes();
				if (rawEntries == null || rawEntries.isEmpty()) {
					return Collections.emptyList();
				}
				List<HostEntryPair> newEntries = new LinkedList<>();
				for (KnownHostEntry entry : rawEntries) {
					AuthorizedKeyEntry keyPart = entry.getKeyEntry();
					if (keyPart == null) {
						continue;
					}
					try {
						PublicKey serverKey = keyPart.resolvePublicKey(null,
								PublicKeyEntryResolver.IGNORING);
						if (serverKey == null) {
							LOG.warn(format(
									SshdText.get().knownHostsUnknownKeyType,
									path, entry.getConfigLine()));
						} else {
							newEntries.add(new HostEntryPair(entry, serverKey));
						}
					} catch (GeneralSecurityException e) {
						LOG.warn(format(SshdText.get().knownHostsInvalidLine,
								path, entry.getConfigLine()));
					}
				}
				return newEntries;
			} catch (FileNotFoundException e) {
				resetReloadAttributes();
				return Collections.emptyList();
			}
		}
	}


	private static class HostKeyHelper extends KnownHostsServerKeyVerifier {

		public HostKeyHelper() {
			super((c, r, s) -> false, new File(".").toPath()); //$NON-NLS-1$
		}

		@Override
		protected KnownHostEntry prepareKnownHostEntry(
				ClientSession clientSession, SocketAddress remoteAddress,
				PublicKey serverKey) throws IOException {
			try {
				return super.prepareKnownHostEntry(clientSession, remoteAddress,
						serverKey);
			} catch (Exception e) {
				throw new IOException(e.getMessage(), e);
			}
		}

		@Override
		protected String prepareModifiedServerKeyLine(
				ClientSession clientSession, SocketAddress remoteAddress,
				KnownHostEntry entry, String curLine, PublicKey expected,
				PublicKey actual) throws IOException {
			try {
				return super.prepareModifiedServerKeyLine(clientSession,
						remoteAddress, entry, curLine, expected, actual);
			} catch (Exception e) {
				throw new IOException(e.getMessage(), e);
			}
		}

		@Override
		protected Collection<SshdSocketAddress> resolveHostNetworkIdentities(
				ClientSession clientSession, SocketAddress remoteAddress) {
			return super.resolveHostNetworkIdentities(clientSession,
					remoteAddress);
		}
	}

}
