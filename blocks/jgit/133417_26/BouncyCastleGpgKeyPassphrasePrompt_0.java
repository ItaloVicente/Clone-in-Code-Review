package org.eclipse.jgit.lib.internal;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.newInputStream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;
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
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.internal.JGitText;

class BouncyCastleGpgKeyLocator {

	private static final Path USER_KEYBOX_PATH = Paths
			.get(System.getProperty("user.home")

	private static final Path USER_SECRET_KEY_DIR = Paths.get(
			System.getProperty("user.home")

	private static final Path USER_PGP_LEGACY_SECRING_FILE = Paths
			.get(System.getProperty("user.home")

	private final String signingKey;

	private BouncyCastleGpgKeyPassphrasePrompt passphrasePrompt;

	public BouncyCastleGpgKeyLocator(String signingKey
			@NonNull BouncyCastleGpgKeyPassphrasePrompt passphrasePrompt) {
		this.signingKey = signingKey;
		this.passphrasePrompt = passphrasePrompt;
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

	private boolean containsSigningKey(String userId) {
		return userId.toLowerCase(Locale.ROOT)
				.contains(signingKey.toLowerCase(Locale.ROOT));
	}

	private PGPPublicKey findPublicKeyByKeyId(KeyBlob keyBlob)
			throws IOException {
		for (KeyInformation keyInfo : keyBlob.getKeyInformation()) {
			if (signingKey.toLowerCase(Locale.ROOT)
					.equals(Hex.toHexString(keyInfo.getKeyID())
							.toLowerCase(Locale.ROOT))) {
				return getFirstPublicKey(keyBlob);
			}
		}
		return null;
	}

	private PGPPublicKey findPublicKeyByUserId(KeyBlob keyBlob)
			throws IOException {
		for (UserID userID : keyBlob.getUserIds()) {
			if (containsSigningKey(userID.getUserIDAsString())) {
				return getFirstPublicKey(keyBlob);
			}
		}
		return null;
	}

	private PGPPublicKey findPublicKeyInKeyBox(Path keyboxFile)
			throws IOException {
		KeyBox keyBox = readKeyBoxFile(keyboxFile);
		for (KeyBlob keyBlob : keyBox.getKeyBlobs()) {
			if (keyBlob.getType() == BlobType.OPEN_PGP_BLOB) {
				PGPPublicKey key = findPublicKeyByKeyId(keyBlob);
				if (key != null) {
					return key;
				}
				key = findPublicKeyByUserId(keyBlob);
				if (key != null) {
					return key;
				}
			}
		}
		return null;
	}

	public PGPSecretKey findSecretKey()
			throws IOException
		if (exists(USER_KEYBOX_PATH)) {
					findPublicKeyInKeyBox(USER_KEYBOX_PATH);

			if (publicKey != null) {
				return findSecretKeyForKeyBoxPublicKey(publicKey);
			}

			throw new PGPException(MessageFormat
					.format(JGitText.get().gpgNoPublicKeyFound
		} else if (exists(USER_PGP_LEGACY_SECRING_FILE)) {
			PGPSecretKey secretKey = findSecretKeyInLegacySecring(signingKey
					USER_PGP_LEGACY_SECRING_FILE);

			if (secretKey != null) {
				return secretKey;
			}

			throw new PGPException(MessageFormat.format(
					JGitText.get().gpgNoKeyInLegacySecring
		}

		throw new PGPException(JGitText.get().gpgNoKeyring);
	}

	private PGPSecretKey findSecretKeyForKeyBoxPublicKey(PGPPublicKey publicKey)
			throws PGPException

		PGPDigestCalculatorProvider calculatorProvider = new JcaPGPDigestCalculatorProviderBuilder()
				.build();

		PBEProtectionRemoverFactory passphraseProvider = new JcePBEProtectionRemoverFactory(
				passphrasePrompt.getPassphrase(publicKey.getFingerprint()));

		try (Stream<Path> keyFiles = Files.walk(USER_SECRET_KEY_DIR)) {
			for (Path keyFile : keyFiles.filter(Files::isRegularFile)
					.collect(Collectors.toList())) {
				PGPSecretKey secretKey = attemptParseSecretKey(keyFile
						calculatorProvider
				if (secretKey != null) {
					return secretKey;
				}
			}

			passphrasePrompt.clear();
			throw new PGPException(MessageFormat.format(
					JGitText.get().gpgNoSecretKeyForPublicKey
					Long.toHexString(publicKey.getKeyID())));
		} catch (RuntimeException e) {
			passphrasePrompt.clear();
			throw e;
		} catch (IOException e) {
			passphrasePrompt.clear();
			throw new PGPException(MessageFormat.format(
					JGitText.get().gpgFailedToParseSecretKey
					USER_SECRET_KEY_DIR.toAbsolutePath())
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
							.toHexString(key.getPublicKey().getFingerprint())
							.toLowerCase(Locale.ROOT);
					if (fingerprint
							.endsWith(signingkey.toLowerCase(Locale.ROOT))) {
						return key;
					}
					Iterator<String> userIDs = key.getUserIDs();
					while (userIDs.hasNext()) {
						String userId = userIDs.next();
						if (containsSigningKey(userId)) {
							return key;
						}
					}
				}
			}
		}
		return null;
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
