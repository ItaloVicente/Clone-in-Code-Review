package org.eclipse.jgit.gpg.bc.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.bouncycastle.bcpg.sig.IssuerFingerprint;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureList;
import org.bouncycastle.openpgp.PGPSignatureSubpacketVector;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.jcajce.JcaPGPObjectFactory;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentVerifierBuilderProvider;
import org.bouncycastle.util.encoders.Hex;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.GpgSignatureVerifier;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.util.LRUMap;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.StringUtils;

public class BouncyCastleGpgSignatureVerifier implements GpgSignatureVerifier {

	private static void registerBouncyCastleProviderIfNecessary() {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}

	public BouncyCastleGpgSignatureVerifier() {
		registerBouncyCastleProviderIfNecessary();
	}


	private static final Object NO_KEY = new Object();

	private LRUMap<String

	private LRUMap<String

	@Override
	public String getName() {
	}

	@Override
	@Nullable
	public SignatureVerification verifySignature(@NonNull RevObject object)
			throws IOException {
		if (object instanceof RevCommit) {
			RevCommit commit = (RevCommit) object;
			byte[] signatureData = commit.getRawGpgSignature();
			if (signatureData == null) {
				return null;
			}
			byte[] raw = commit.getRawBuffer();
			byte[] header = { 'g'
			int start = RawParseUtils.headerStart(header
			if (start < 0) {
				return null;
			}
			int end = RawParseUtils.headerEnd(raw
			start -= header.length + 1;
			if (end < raw.length) {
				end++;
			}
			byte[] data = new byte[raw.length - (end - start)];
			System.arraycopy(raw
			System.arraycopy(raw
			return verify(data
		} else if (object instanceof RevTag) {
			RevTag tag = (RevTag) object;
			byte[] signatureData = tag.getRawGpgSignature();
			if (signatureData == null) {
				return null;
			}
			byte[] raw = tag.getRawBuffer();
			byte[] data = Arrays.copyOfRange(raw
					raw.length - signatureData.length);
			return verify(data
		}
		return null;
	}

	private SignatureVerification verify(byte[] data
			throws IOException {
		PGPSignature signature = null;
		String fingerprint = null;
		String signer = null;
		String keyId = null;
		try (InputStream sigIn = PGPUtil
				.getDecoderStream(new ByteArrayInputStream(signatureData))) {
			JcaPGPObjectFactory pgpFactory = new JcaPGPObjectFactory(sigIn);
			Object obj = pgpFactory.nextObject();
			if (obj instanceof PGPCompressedData) {
				obj = new JcaPGPObjectFactory(
						((PGPCompressedData) obj).getDataStream()).nextObject();
			}
			if (obj instanceof PGPSignatureList) {
				signature = ((PGPSignatureList) obj).get(0);
				if (signature.hasSubpackets()) {
					PGPSignatureSubpacketVector packets = signature
							.getHashedSubPackets();
					IssuerFingerprint fingerprintPacket = packets
							.getIssuerFingerprint();
					if (fingerprintPacket != null) {
						fingerprint = Hex
								.toHexString(fingerprintPacket.getFingerprint())
								.toLowerCase(Locale.ROOT);
					}
					signer = packets.getSignerUserID();
					if (signer != null) {
						signer = BouncyCastleGpgSigner.extractSignerId(signer);
					}
				}
				keyId = Long.toUnsignedString(signature.getKeyID()
						.toLowerCase(Locale.ROOT);
			} else {
				throw new JGitInternalException(BCText.get().nonSignatureError);
			}
		} catch (PGPException e) {
			throw new JGitInternalException(BCText.get().signatureParseError
					e);
		}
		Date signatureCreatedAt = signature.getCreationTime();
		if (fingerprint == null && signer == null && keyId == null) {
			return new VerificationResult(signatureCreatedAt
					false
					BCText.get().signatureNoKeyInfo);
		}
		if (fingerprint != null && keyId != null
				&& !fingerprint.endsWith(keyId)) {
			return new VerificationResult(signatureCreatedAt
					null
					MessageFormat.format(BCText.get().signatureInconsistent
							keyId
		}
		if (fingerprint == null && keyId != null) {
			fingerprint = keyId;
		}
		String keySpec = '<' + signer + '>';
		Object cached = null;
		PGPPublicKey publicKey = null;
		try {
			cached = byFingerprint.get(fingerprint);
			if (cached != null) {
				if (cached instanceof PGPPublicKey) {
					publicKey = (PGPPublicKey) cached;
				}
			} else if (!StringUtils.isEmptyOrNull(signer)) {
				cached = bySigner.get(signer);
				if (cached != null) {
					if (cached instanceof PGPPublicKey) {
						publicKey = (PGPPublicKey) cached;
					}
				}
			}
			if (cached == null) {
				publicKey = BouncyCastleGpgKeyLocator.findPublicKey(fingerprint
						keySpec);
			}
		} catch (IOException | PGPException e) {
			throw new JGitInternalException(
					BCText.get().signatureKeyLookupError
		}
		if (publicKey == null) {
			if (cached == null) {
				byFingerprint.put(fingerprint
				byFingerprint.put(keyId
				if (signer != null) {
					bySigner.put(signer
				}
			}
			return new VerificationResult(signatureCreatedAt
					fingerprint
					BCText.get().signatureNoPublicKey);
		}
		if (cached == null) {
			byFingerprint.put(fingerprint
			byFingerprint.put(keyId
			if (signer != null) {
				bySigner.put(signer
			}
		}
		String user = null;
		Iterator<String> userIds = publicKey.getUserIDs();
		if (!StringUtils.isEmptyOrNull(signer)) {
			while (userIds.hasNext()) {
				String userId = userIds.next();
				if (BouncyCastleGpgKeyLocator.containsSigningKey(userId
						keySpec)) {
					user = userId;
					break;
				}
			}
		}
		if (user == null) {
			userIds = publicKey.getUserIDs();
			if (userIds.hasNext()) {
				user = userIds.next();
			}
		}
		boolean expired = false;
		long validFor = publicKey.getValidSeconds();
		if (validFor > 0 && signatureCreatedAt != null) {
			Instant expiredAt = publicKey.getCreationTime().toInstant()
					.plusSeconds(validFor);
			expired = expiredAt.isBefore(signatureCreatedAt.toInstant());
		}
		byte[] trustData = publicKey.getTrustData();
		TrustLevel trust = parseGpgTrustPacket(trustData);
		boolean verified = false;
		try {
			signature.init(
					new JcaPGPContentVerifierBuilderProvider()
							.setProvider(BouncyCastleProvider.PROVIDER_NAME)
					publicKey);
			signature.update(data);
			verified = signature.verify();
		} catch (PGPException e) {
			throw new JGitInternalException(
					BCText.get().signatureVerificationError
		}
		return new VerificationResult(signatureCreatedAt
				verified
	}

	private TrustLevel parseGpgTrustPacket(byte[] packet) {
		if (packet == null || packet.length < 6) {
			return TrustLevel.UNKNOWN;
		}
		if (packet[2] != 'g' || packet[3] != 'p' || packet[4] != 'g') {
			return TrustLevel.UNKNOWN;
		}
		int trust = packet[0] & 0x0F;
		switch (trust) {
			return TrustLevel.UNKNOWN;
		case 3:
			return TrustLevel.NEVER;
		case 4:
			return TrustLevel.MARGINAL;
		case 5:
			return TrustLevel.FULL;
		case 6:
			return TrustLevel.ULTIMATE;
		default:
			return TrustLevel.UNKNOWN;
		}
	}

	@Override
	public void clear() {
		byFingerprint.clear();
		bySigner.clear();
	}

	private static class VerificationResult implements SignatureVerification {

		private final Date creationDate;

		private final String signer;

		private final String keyUser;

		private final String fingerprint;

		private final boolean verified;

		private final boolean expired;

		private final @NonNull TrustLevel trustLevel;

		private final String message;

		public VerificationResult(Date creationDate
				String fingerprint
				boolean expired
			this.creationDate = creationDate;
			this.signer = signer;
			this.fingerprint = fingerprint;
			this.keyUser = user;
			this.verified = verified;
			this.expired = expired;
			this.trustLevel = trust;
			this.message = message;
		}

		@Override
		public Date getCreationDate() {
			return creationDate;
		}

		@Override
		public String getSigner() {
			return signer;
		}

		@Override
		public String getKeyUser() {
			return keyUser;
		}

		@Override
		public String getKeyFingerprint() {
			return fingerprint;
		}

		@Override
		public boolean isExpired() {
			return expired;
		}

		@Override
		public TrustLevel getTrustLevel() {
			return trustLevel;
		}

		@Override
		public String getMessage() {
			return message;
		}

		@Override
		public boolean getVerified() {
			return verified;
		}
	}
}
