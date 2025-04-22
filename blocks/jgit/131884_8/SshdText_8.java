package org.eclipse.jgit.internal.transport.sshd;

import static java.text.MessageFormat.format;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.ArrayList;
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
import org.eclipse.jgit.transport.sshd.JGitHostConfigEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenSshServerKeyVerifier implements ServerKeyVerifier {


	private static final Logger LOG = LoggerFactory
			.getLogger(OpenSshServerKeyVerifier.class);


	private final boolean askAboutNewFile;

	private final Map<Path

	private final List<HostKeyFile> defaultFiles = new ArrayList<>();

	private enum ModifiedKeyHandling {
		DENY
	}

	public OpenSshServerKeyVerifier(boolean askAboutNewFile
		if (defaultFiles != null) {
			for (File file : defaultFiles) {
				Path p = file.toPath();
				HostKeyFile newFile = new HostKeyFile(p);
				knownHostsFiles.put(p
				this.defaultFiles.add(newFile);
			}
		}
		this.askAboutNewFile = askAboutNewFile;
	}

	@Override
	public boolean verifyServerKey(ClientSession clientSession
			SocketAddress remoteAddress
		List<HostKeyFile> filesToUse = defaultFiles;
		if (clientSession instanceof JGitClientSession) {
			HostConfigEntry entry = ((JGitClientSession) clientSession)
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
		AskUser ask = new AskUser();
		HostEntryPair[] modified = { null };
		Path path = null;
		HostKeyHelper helper = new HostKeyHelper();
		for (HostKeyFile file : filesToUse) {
			try {
				if (find(clientSession
						modified
					return true;
				}
			} catch (RevokedKeyException e) {
				ask.revokedKey(clientSession
						file.getPath());
				return false;
			}
			if (path == null && modified[0] != null) {
				path = file.getPath();
			}
		}
		if (modified[0] != null) {
			ModifiedKeyHandling toDo = ask.acceptModifiedServerKey(
					clientSession
					serverKey
			if (toDo == ModifiedKeyHandling.ALLOW_AND_STORE) {
				try {
					updateModifiedServerKey(clientSession
							serverKey
					knownHostsFiles.get(path).resetReloadAttributes();
				} catch (IOException e) {
					LOG.warn(format(SshdText.get().knownHostsCouldNotUpdate
							path));
				}
			}
			if (toDo == ModifiedKeyHandling.DENY) {
				return false;
			}
			return true;
		} else if (ask.acceptUnknownKey(clientSession
				serverKey)) {
			if (!filesToUse.isEmpty()) {
				HostKeyFile toUpdate = filesToUse.get(0);
				path = toUpdate.getPath();
				try {
					updateKnownHostsFile(clientSession
							serverKey
					toUpdate.resetReloadAttributes();
				} catch (IOException e) {
					LOG.warn(format(SshdText.get().knownHostsCouldNotUpdate
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

	private boolean find(ClientSession clientSession
			SocketAddress remoteAddress
			List<HostEntryPair> entries
			HostKeyHelper helper) throws RevokedKeyException {
		Collection<SshdSocketAddress> candidates = helper
				.resolveHostNetworkIdentities(clientSession
		for (HostEntryPair current : entries) {
			KnownHostEntry entry = current.getHostEntry();
			for (SshdSocketAddress host : candidates) {
				if (entry.isHostMatch(host.getHostName()
					boolean isRevoked = MARKER_REVOKED
							.equals(entry.getMarker());
					if (KeyUtils.compareKeys(serverKey
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
				HostKeyFile file = knownHostsFiles.computeIfAbsent(path
						p -> new HostKeyFile(path));
				userFiles.add(file);
			} catch (InvalidPathException e) {
				LOG.warn(format(SshdText.get().knownHostsInvalidPath
						name));
			}
		}
		return userFiles;
	}

	private void updateKnownHostsFile(ClientSession clientSession
			SocketAddress remoteAddress
			HostKeyHelper updater)
			throws IOException {
		KnownHostEntry entry = updater.prepareKnownHostEntry(clientSession
				remoteAddress
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
				if (!askUser(provider
						format(SshdText.get().knownHostsUserAskCreationPrompt
								path)
						format(SshdText.get().knownHostsUserAskCreationMsg
								path))) {
					return;
				}
			}
		}
		LockFile lock = new LockFile(path.toFile());
		if (lock.lockForAppend()) {
			try {
				try (BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(lock.getOutputStream()
								StandardCharsets.UTF_8))) {
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
			LOG.warn(format(SshdText.get().knownHostsFileLockedUpdate
					path));
		}
	}

	private void updateModifiedServerKey(ClientSession clientSession
			SocketAddress remoteAddress
			HostEntryPair entry
			throws IOException {
		KnownHostEntry hostEntry = entry.getHostEntry();
		String oldLine = hostEntry.getConfigLine();
		String newLine = helper.prepareModifiedServerKeyLine(clientSession
				remoteAddress
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
						new OutputStreamWriter(lock.getOutputStream()
								StandardCharsets.UTF_8));
						BufferedReader reader = Files.newBufferedReader(path
								StandardCharsets.UTF_8)) {
					boolean done = false;
					String line;
					while ((line = reader.readLine()) != null) {
						String toWrite = line;
						if (!done) {
							int pos = line.indexOf('#');
							String toTest = pos < 0 ? line
									: line.substring(0
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
			LOG.warn(format(SshdText.get().knownHostsFileLockedUpdate
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

	private static boolean askUser(CredentialsProvider provider
			String prompt
		List<CredentialItem> items = new ArrayList<>(messages.length + 1);
		for (String message : messages) {
			items.add(new CredentialItem.InformationalMessage(message));
		}
		if (prompt != null) {
			CredentialItem.YesNoType answer = new CredentialItem.YesNoType(
					prompt);
			items.add(answer);
			return provider.get(uri
		} else {
			return provider.get(uri
		}
	}

	private static class AskUser {

		private enum Check {
			ASK
		}

		@SuppressWarnings("nls")
		private Check checkMode(ClientSession session
				SocketAddress remoteAddress
			if (!(remoteAddress instanceof InetSocketAddress)) {
				return Check.DENY;
			}
			if (session instanceof JGitClientSession) {
				HostConfigEntry entry = ((JGitClientSession) session)
						.getHostConfigEntry();
				String value = entry.getProperty(
						SshConstants.STRICT_HOST_KEY_CHECKING
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

		public void revokedKey(ClientSession clientSession
				SocketAddress remoteAddress
			CredentialsProvider provider = getCredentialsProvider(
					clientSession);
			if (provider == null) {
				return;
			}
			InetSocketAddress remote = (InetSocketAddress) remoteAddress;
			URIish uri = JGitUserInteraction.toURI(clientSession.getUsername()
					remote);
			String sha256 = KeyUtils.getFingerPrint(BuiltinDigests.sha256
					serverKey);
			String md5 = KeyUtils.getFingerPrint(BuiltinDigests.md5
			String keyAlgorithm = serverKey.getAlgorithm();
			askUser(provider
					format(SshdText.get().knownHostsRevokedKeyMsg
							remote.getHostString()
					format(SshdText.get().knownHostsKeyFingerprints
							keyAlgorithm)
					md5
		}

		public boolean acceptUnknownKey(ClientSession clientSession
				SocketAddress remoteAddress
			Check check = checkMode(clientSession
			if (check != Check.ASK) {
				return check == Check.ALLOW;
			}
			CredentialsProvider provider = getCredentialsProvider(
					clientSession);
			InetSocketAddress remote = (InetSocketAddress) remoteAddress;
			String sha256 = KeyUtils.getFingerPrint(BuiltinDigests.sha256
					serverKey);
			String md5 = KeyUtils.getFingerPrint(BuiltinDigests.md5
			String keyAlgorithm = serverKey.getAlgorithm();
			String remoteHost = remote.getHostString();
			URIish uri = JGitUserInteraction.toURI(clientSession.getUsername()
					remote);
			String prompt = SshdText.get().knownHostsUnknownKeyPrompt;
			return askUser(provider
					format(SshdText.get().knownHostsUnknownKeyMsg
							remoteHost)
					format(SshdText.get().knownHostsKeyFingerprints
							keyAlgorithm)
					md5
		}

		public ModifiedKeyHandling acceptModifiedServerKey(
				ClientSession clientSession
				SocketAddress remoteAddress
				PublicKey actual
			Check check = checkMode(clientSession
			if (check == Check.ALLOW) {
				return ModifiedKeyHandling.ALLOW;
			}
			InetSocketAddress remote = (InetSocketAddress) remoteAddress;
			String keyAlgorithm = actual.getAlgorithm();
			String remoteHost = remote.getHostString();
			URIish uri = JGitUserInteraction.toURI(clientSession.getUsername()
					remote);
			List<String> messages = new ArrayList<>();
			String warning = format(
					SshdText.get().knownHostsModifiedKeyWarning
					keyAlgorithm
					KeyUtils.getFingerPrint(BuiltinDigests.md5
					KeyUtils.getFingerPrint(BuiltinDigests.sha256
					KeyUtils.getFingerPrint(BuiltinDigests.md5
					KeyUtils.getFingerPrint(BuiltinDigests.sha256
				messages.add(line);
			}

			CredentialsProvider provider = getCredentialsProvider(
					clientSession);
			if (check == Check.DENY) {
				if (provider != null) {
					messages.add(format(
							SshdText.get().knownHostsModifiedKeyDenyMsg
					askUser(provider
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
			if (provider.get(uri
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
						LOG.warn(format(SshdText.get().knownHostsFileLockedRead
								path));
					}
				}
			} catch (IOException e) {
				LOG.warn(format(SshdText.get().knownHostsFileReadFailed
			}
			return Collections.unmodifiableList(entries);
		}

		private List<HostEntryPair> reload(Path path) throws IOException {
			try {
				List<KnownHostEntry> rawEntries = KnownHostEntry
						.readKnownHostEntries(path);
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
						PublicKey serverKey = keyPart.resolvePublicKey(
								PublicKeyEntryResolver.IGNORING);
						if (serverKey == null) {
							LOG.warn(format(
									SshdText.get().knownHostsUnknownKeyType
									getPath()
						} else {
							newEntries.add(new HostEntryPair(entry
						}
					} catch (GeneralSecurityException e) {
						LOG.warn(format(SshdText.get().knownHostsInvalidLine
								getPath()
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
			super((c
		}

		@Override
		protected KnownHostEntry prepareKnownHostEntry(
				ClientSession clientSession
				PublicKey serverKey) throws IOException {
			try {
				return super.prepareKnownHostEntry(clientSession
						serverKey);
			} catch (Exception e) {
				throw new IOException(e.getMessage()
			}
		}

		@Override
		protected String prepareModifiedServerKeyLine(
				ClientSession clientSession
				KnownHostEntry entry
				PublicKey actual) throws IOException {
			try {
				return super.prepareModifiedServerKeyLine(clientSession
						remoteAddress
			} catch (Exception e) {
				throw new IOException(e.getMessage()
			}
		}

		@Override
		protected Collection<SshdSocketAddress> resolveHostNetworkIdentities(
				ClientSession clientSession
			return super.resolveHostNetworkIdentities(clientSession
					remoteAddress);
		}
	}

}
