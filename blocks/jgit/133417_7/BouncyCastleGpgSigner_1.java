package org.eclipse.jgit.lib.internal;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.newInputStream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bouncycastle.gpg.SExprParser;
import org.bouncycastle.gpg.keybox.BlobType;
import org.bouncycastle.gpg.keybox.KeyBlob;
import org.bouncycastle.gpg.keybox.KeyBox;
import org.bouncycastle.gpg.keybox.KeyInformation;
import org.bouncycastle.gpg.keybox.PublicKeyRingBlob;
import org.bouncycastle.gpg.keybox.UserID;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.PBEProtectionRemoverFactory;
import org.bouncycastle.openpgp.operator.PGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePBEProtectionRemoverFactory;
import org.bouncycastle.util.encoders.Hex;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.transport.CredentialItem.CharArrayType;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;

class BouncyCastleGpgKeyHelper {

	private static final Path USER_KEYBOX_PATH = Paths
			.get(System.getProperty("user.home")

	private static final Path USER_SECRET_KEY_DIR = Paths.get(
			System.getProperty("user.home")

	private static final Path USER_PGP_LEGACY_SECRING_FILE = Paths
			.get(System.getProperty("user.home")

	private final String signingKey;

	private CharArrayType cachedPassphrase;

	public BouncyCastleGpgKeyHelper(String signingKey) {
		this.signingKey = signingKey;
	}

	private PGPSecretKey attemptParseSecretKey(Path keyFile
			PGPDigestCalculatorProvider calculatorProvider
			PBEProtectionRemoverFactory passphraseProvider
			PGPPublicKey publicKey) throws IOException {
		try (InputStream in = newInputStream(keyFile)) {
			return new SExprParser(calculatorProvider).parseSecretKey(
					new BufferedInputStream(in)
		} catch (PGPException | ClassCastException e) {
			return null;
		}
	}

	private URIish createURI() {
		URIish uri = new URIish();
		return uri;
	}

	private PGPPublicKey findPublicKeyInKeyBox(Path keyboxFile)
			throws IOException {
		KeyBox keyBox = readKeyBoxFile(keyboxFile);
		for (KeyBlob keyBlob : keyBox.getKeyBlobs()) {
			if (keyBlob.getType() == BlobType.OPEN_PGP_BLOB) {
				for (KeyInformation keyInfo : keyBlob.getKeyInformation()) {
					if (signingKey
							.equals(Hex.toHexString(keyInfo.getKeyID()))) {
						return getFirstPublicKey(keyBlob);
					}
				}
				for (UserID userID : keyBlob.getUserIds()) {
					if (signingKey.equalsIgnoreCase(userID.getUserIDAsString()))
						return getFirstPublicKey(keyBlob);
				}
			}
		}
		return null;
	}

	public PGPSecretKey findSecretKey() throws IOException
		if (exists(USER_KEYBOX_PATH)) {
					findPublicKeyInKeyBox(USER_KEYBOX_PATH);

			if (publicKey == null)
				throw new PGPException(

			PGPSecretKey secretKey = findSecretKeyForKeyBoxPublicKey(publicKey);
			if (secretKey != null)
				return secretKey;

			throw new PGPException(
							+ Long.toHexString(publicKey.getKeyID()));
		} else if (exists(USER_PGP_LEGACY_SECRING_FILE)) {
			PGPSecretKey secretKey = findSecretKeyInLegacySecring(signingKey
					USER_PGP_LEGACY_SECRING_FILE);

			if (secretKey != null)
				return secretKey;

			throw new PGPException(
							+ signingKey);
		}

		throw new PGPException(
	}

	private PGPSecretKey findSecretKeyForKeyBoxPublicKey(PGPPublicKey publicKey)
			throws PGPException {

		PGPDigestCalculatorProvider calculatorProvider = new JcaPGPDigestCalculatorProviderBuilder()
				.build();
		CharArrayType passphrase = new CharArrayType(
				JGitText.get().credentialPassphrase
		CredentialsProvider credentialsProvider = CredentialsProvider
				.getDefault();
		if (credentialsProvider == null)

		if (credentialsProvider.get(createURI()
			PBEProtectionRemoverFactory passphraseProvider = new JcePBEProtectionRemoverFactory(
					passphrase.getValue());

			try (Stream<Path> keyFiles = Files.walk(USER_SECRET_KEY_DIR)) {
				for (Path keyFile : keyFiles.filter(Files::isRegularFile)
						.collect(Collectors.toList())) {
					PGPSecretKey secretKey = attemptParseSecretKey(keyFile
							calculatorProvider
					if (secretKey != null) {
						cachedPassphrase = passphrase;
						return secretKey;
					}
				}

				passphrase.clear();
				return null;
			} catch (RuntimeException e) {
				passphrase.clear();
				throw e;
			} catch (IOException e) {
				passphrase.clear();
				throw new PGPException(
								+ USER_SECRET_KEY_DIR.toAbsolutePath()
										.toString()
						e);
			}
		} else {
		}

	}

	private PGPSecretKey findSecretKeyInLegacySecring(String signingkey
			Path secringFile) throws IOException

		try (InputStream in = newInputStream(secringFile)) {
			PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
					PGPUtil.getDecoderStream(new BufferedInputStream(in))
					new JcaKeyFingerprintCalculator());

			Iterator<PGPSecretKeyRing> keyrings = pgpSec.getKeyRings();
			while (keyrings.hasNext()) {
				PGPSecretKeyRing keyRing = keyrings.next();
				Iterator<PGPSecretKey> keys = keyRing.getSecretKeys();
				while (keys.hasNext()) {
					PGPSecretKey key = keys.next();
					String fingerprint = Hex
							.toHexString(key.getPublicKey().getFingerprint());
					if (fingerprint.endsWith(signingkey)) {
						return key;
					}
					Iterator<String> userIDs = key.getUserIDs();
					while (userIDs.hasNext()) {
						String userId = userIDs.next();
						if (signingkey.equals(userId)) {
							return key;
						}
					}
				}
			}
		}
		throw new PGPException(
						+ signingkey);
	}

	public CharArrayType getCachedPassphrase() {
		return cachedPassphrase;
	}

	private PGPPublicKey getFirstPublicKey(KeyBlob keyBlob) throws IOException {
		return ((PublicKeyRingBlob) keyBlob).getPGPPublicKeyRing()
				.getPublicKey();
	}

	private KeyBox readKeyBoxFile(Path keyboxFile) throws IOException {
		KeyBox keyBox;
		try (InputStream in = new BufferedInputStream(
				newInputStream(keyboxFile))) {
			keyBox = new KeyBox(in
		}
		return keyBox;
	}
}
