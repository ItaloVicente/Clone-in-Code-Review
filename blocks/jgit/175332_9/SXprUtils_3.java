package org.eclipse.jgit.gpg.bc.internal.keys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;

import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.bcpg.DSAPublicBCPGKey;
import org.bouncycastle.bcpg.DSASecretBCPGKey;
import org.bouncycastle.bcpg.ECDSAPublicBCPGKey;
import org.bouncycastle.bcpg.ECPublicBCPGKey;
import org.bouncycastle.bcpg.ECSecretBCPGKey;
import org.bouncycastle.bcpg.ElGamalPublicBCPGKey;
import org.bouncycastle.bcpg.ElGamalSecretBCPGKey;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.PublicKeyAlgorithmTags;
import org.bouncycastle.bcpg.PublicKeyPacket;
import org.bouncycastle.bcpg.RSAPublicBCPGKey;
import org.bouncycastle.bcpg.RSASecretBCPGKey;
import org.bouncycastle.bcpg.S2K;
import org.bouncycastle.bcpg.SecretKeyPacket;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.operator.KeyFingerPrintCalculator;
import org.bouncycastle.openpgp.operator.PBEProtectionRemoverFactory;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.PGPDigestCalculatorProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

@SuppressWarnings("nls")
public class SExprParser {
	private final PGPDigestCalculatorProvider digestProvider;

	public SExprParser(PGPDigestCalculatorProvider digestProvider) {
		this.digestProvider = digestProvider;
	}

	public PGPSecretKey parseSecretKey(InputStream inputStream
			PBEProtectionRemoverFactory keyProtectionRemoverFactory
			PGPPublicKey pubKey) throws IOException
		SXprUtils.skipOpenParenthesis(inputStream);

		String type;

		type = SXprUtils.readString(inputStream
		if (type.equals("protected-private-key")
				|| type.equals("private-key")) {
			SXprUtils.skipOpenParenthesis(inputStream);

			String keyType = SXprUtils.readString(inputStream
					inputStream.read());
			if (keyType.equals("ecc")) {
				SXprUtils.skipOpenParenthesis(inputStream);

				String curveID = SXprUtils.readString(inputStream
						inputStream.read());
				String curveName = SXprUtils.readString(inputStream
						inputStream.read());

				SXprUtils.skipCloseParenthesis(inputStream);

				byte[] qVal;

				SXprUtils.skipOpenParenthesis(inputStream);

				type = SXprUtils.readString(inputStream
				if (type.equals("q")) {
					qVal = SXprUtils.readBytes(inputStream
				} else {
					throw new PGPException("no q value found");
				}

				SXprUtils.skipCloseParenthesis(inputStream);

				BigInteger d = processECSecretKey(inputStream
						curveName

				if (curveName.startsWith("NIST ")) {
					curveName = curveName.substring("NIST ".length());
				}

				ECPublicBCPGKey basePubKey = new ECDSAPublicBCPGKey(
						ECNamedCurveTable.getOID(curveName)
						new BigInteger(1
				ECPublicBCPGKey assocPubKey = (ECPublicBCPGKey) pubKey
						.getPublicKeyPacket().getKey();
				if (!basePubKey.getCurveOID().equals(assocPubKey.getCurveOID())
						|| !basePubKey.getEncodedPoint()
								.equals(assocPubKey.getEncodedPoint())) {
					throw new PGPException(
							"passed in public key does not match secret key");
				}

				return new PGPSecretKey(
						new SecretKeyPacket(pubKey.getPublicKeyPacket()
								SymmetricKeyAlgorithmTags.NULL
								new ECSecretBCPGKey(d).getEncoded())
						pubKey);
			} else if (keyType.equals("dsa")) {
				BigInteger p = readBigInteger("p"
				BigInteger q = readBigInteger("q"
				BigInteger g = readBigInteger("g"

				BigInteger y = readBigInteger("y"

				BigInteger x = processDSASecretKey(inputStream
						keyProtectionRemoverFactory);

				DSAPublicBCPGKey basePubKey = new DSAPublicBCPGKey(p
				DSAPublicBCPGKey assocPubKey = (DSAPublicBCPGKey) pubKey
						.getPublicKeyPacket().getKey();
				if (!basePubKey.getP().equals(assocPubKey.getP())
						|| !basePubKey.getQ().equals(assocPubKey.getQ())
						|| !basePubKey.getG().equals(assocPubKey.getG())
						|| !basePubKey.getY().equals(assocPubKey.getY())) {
					throw new PGPException(
							"passed in public key does not match secret key");
				}
				return new PGPSecretKey(
						new SecretKeyPacket(pubKey.getPublicKeyPacket()
								SymmetricKeyAlgorithmTags.NULL
								new DSASecretBCPGKey(x).getEncoded())
						pubKey);
			} else if (keyType.equals("elg")) {
				BigInteger p = readBigInteger("p"
				BigInteger g = readBigInteger("g"

				BigInteger y = readBigInteger("y"

				BigInteger x = processElGamalSecretKey(inputStream
						keyProtectionRemoverFactory);

				ElGamalPublicBCPGKey basePubKey = new ElGamalPublicBCPGKey(p
						y);
				ElGamalPublicBCPGKey assocPubKey = (ElGamalPublicBCPGKey) pubKey
						.getPublicKeyPacket().getKey();
				if (!basePubKey.getP().equals(assocPubKey.getP())
						|| !basePubKey.getG().equals(assocPubKey.getG())
						|| !basePubKey.getY().equals(assocPubKey.getY())) {
					throw new PGPException(
							"passed in public key does not match secret key");
				}

				return new PGPSecretKey(
						new SecretKeyPacket(pubKey.getPublicKeyPacket()
								SymmetricKeyAlgorithmTags.NULL
								new ElGamalSecretBCPGKey(x).getEncoded())
						pubKey);
			} else if (keyType.equals("rsa")) {
				BigInteger n = readBigInteger("n"
				BigInteger e = readBigInteger("e"

				BigInteger[] values = processRSASecretKey(inputStream
						keyProtectionRemoverFactory);

				RSAPublicBCPGKey basePubKey = new RSAPublicBCPGKey(n
				RSAPublicBCPGKey assocPubKey = (RSAPublicBCPGKey) pubKey
						.getPublicKeyPacket().getKey();
				if (!basePubKey.getModulus().equals(assocPubKey.getModulus())
						|| !basePubKey.getPublicExponent()
								.equals(assocPubKey.getPublicExponent())) {
					throw new PGPException(
							"passed in public key does not match secret key");
				}

				return new PGPSecretKey(new SecretKeyPacket(
						pubKey.getPublicKeyPacket()
						SymmetricKeyAlgorithmTags.NULL
						new RSASecretBCPGKey(values[0]
								.getEncoded())
						pubKey);
			} else {
				throw new PGPException("unknown key type: " + keyType);
			}
		}

		throw new PGPException("unknown key type found");
	}

	public PGPSecretKey parseSecretKey(InputStream inputStream
			PBEProtectionRemoverFactory keyProtectionRemoverFactory
			KeyFingerPrintCalculator fingerPrintCalculator)
			throws IOException
		SXprUtils.skipOpenParenthesis(inputStream);

		String type;

		type = SXprUtils.readString(inputStream
		if (type.equals("protected-private-key")
				|| type.equals("private-key")) {
			SXprUtils.skipOpenParenthesis(inputStream);

			String keyType = SXprUtils.readString(inputStream
					inputStream.read());
			if (keyType.equals("ecc")) {
				SXprUtils.skipOpenParenthesis(inputStream);

				String curveID = SXprUtils.readString(inputStream
						inputStream.read());
				String curveName = SXprUtils.readString(inputStream
						inputStream.read());

				if (curveName.startsWith("NIST ")) {
					curveName = curveName.substring("NIST ".length());
				}

				SXprUtils.skipCloseParenthesis(inputStream);

				byte[] qVal;

				SXprUtils.skipOpenParenthesis(inputStream);

				type = SXprUtils.readString(inputStream
				if (type.equals("q")) {
					qVal = SXprUtils.readBytes(inputStream
				} else {
					throw new PGPException("no q value found");
				}

				PublicKeyPacket pubPacket = new PublicKeyPacket(
						PublicKeyAlgorithmTags.ECDSA
						new ECDSAPublicBCPGKey(
								ECNamedCurveTable.getOID(curveName)
								new BigInteger(1

				SXprUtils.skipCloseParenthesis(inputStream);

				BigInteger d = processECSecretKey(inputStream
						curveName

				return new PGPSecretKey(
						new SecretKeyPacket(pubPacket
								SymmetricKeyAlgorithmTags.NULL
								new ECSecretBCPGKey(d).getEncoded())
						new PGPPublicKey(pubPacket
			} else if (keyType.equals("dsa")) {
				BigInteger p = readBigInteger("p"
				BigInteger q = readBigInteger("q"
				BigInteger g = readBigInteger("g"

				BigInteger y = readBigInteger("y"

				BigInteger x = processDSASecretKey(inputStream
						keyProtectionRemoverFactory);

				PublicKeyPacket pubPacket = new PublicKeyPacket(
						PublicKeyAlgorithmTags.DSA
						new DSAPublicBCPGKey(p

				return new PGPSecretKey(
						new SecretKeyPacket(pubPacket
								SymmetricKeyAlgorithmTags.NULL
								new DSASecretBCPGKey(x).getEncoded())
						new PGPPublicKey(pubPacket
			} else if (keyType.equals("elg")) {
				BigInteger p = readBigInteger("p"
				BigInteger g = readBigInteger("g"

				BigInteger y = readBigInteger("y"

				BigInteger x = processElGamalSecretKey(inputStream
						keyProtectionRemoverFactory);

				PublicKeyPacket pubPacket = new PublicKeyPacket(
						PublicKeyAlgorithmTags.ELGAMAL_ENCRYPT
						new ElGamalPublicBCPGKey(p

				return new PGPSecretKey(
						new SecretKeyPacket(pubPacket
								SymmetricKeyAlgorithmTags.NULL
								new ElGamalSecretBCPGKey(x).getEncoded())
						new PGPPublicKey(pubPacket
			} else if (keyType.equals("rsa")) {
				BigInteger n = readBigInteger("n"
				BigInteger e = readBigInteger("e"

				BigInteger[] values = processRSASecretKey(inputStream
						keyProtectionRemoverFactory);

				PublicKeyPacket pubPacket = new PublicKeyPacket(
						PublicKeyAlgorithmTags.RSA_GENERAL
						new RSAPublicBCPGKey(n

				return new PGPSecretKey(
						new SecretKeyPacket(pubPacket
								SymmetricKeyAlgorithmTags.NULL
								new RSASecretBCPGKey(values[0]
										values[2]).getEncoded())
						new PGPPublicKey(pubPacket
			} else {
				throw new PGPException("unknown key type: " + keyType);
			}
		}

		throw new PGPException("unknown key type found");
	}

	private BigInteger readBigInteger(String expectedType
			InputStream inputStream) throws IOException
		SXprUtils.skipOpenParenthesis(inputStream);

		String type = SXprUtils.readString(inputStream
		if (!type.equals(expectedType)) {
			throw new PGPException(expectedType + " value expected");
		}

		byte[] nBytes = SXprUtils.readBytes(inputStream
		BigInteger v = new BigInteger(1

		SXprUtils.skipCloseParenthesis(inputStream);

		return v;
	}

	private static byte[][] extractData(InputStream inputStream
			PBEProtectionRemoverFactory keyProtectionRemoverFactory)
			throws PGPException
		byte[] data;
		byte[] protectedAt = null;

		SXprUtils.skipOpenParenthesis(inputStream);

		String type = SXprUtils.readString(inputStream
		if (type.equals("protected")) {
			String protection = SXprUtils.readString(inputStream
					inputStream.read());

			SXprUtils.skipOpenParenthesis(inputStream);

			S2K s2k = SXprUtils.parseS2K(inputStream);

			byte[] iv = SXprUtils.readBytes(inputStream

			SXprUtils.skipCloseParenthesis(inputStream);

			byte[] secKeyData = SXprUtils.readBytes(inputStream
					inputStream.read());

			SXprUtils.skipCloseParenthesis(inputStream);

			PBESecretKeyDecryptor keyDecryptor = keyProtectionRemoverFactory
					.createDecryptor(protection);

			byte[] key = keyDecryptor.makeKeyFromPassPhrase(
					SymmetricKeyAlgorithmTags.AES_128

			data = keyDecryptor.recoverKeyData(
					SymmetricKeyAlgorithmTags.AES_128
					secKeyData.length);

			if (inputStream.read() == '(') {
				ByteArrayOutputStream bOut = new ByteArrayOutputStream();

				bOut.write('(');
				int ch;
				while ((ch = inputStream.read()) >= 0 && ch != ')') {
					bOut.write(ch);
				}

				if (ch != ')') {
					throw new IOException("unexpected end to SExpr");
				}

				bOut.write(')');

				protectedAt = bOut.toByteArray();
			}

			SXprUtils.skipCloseParenthesis(inputStream);
			SXprUtils.skipCloseParenthesis(inputStream);
		} else if (type.equals("d") || type.equals("x")) {
			return null;
		} else {
			throw new PGPException("protected block not found");
		}

		return new byte[][] { data
	}

	private BigInteger processDSASecretKey(InputStream inputStream
			BigInteger p
			PBEProtectionRemoverFactory keyProtectionRemoverFactory)
			throws IOException
		String type;
		byte[][] basicData = extractData(inputStream
				keyProtectionRemoverFactory);

		if (basicData == null) {
			byte[] nBytes = SXprUtils.readBytes(inputStream
					inputStream.read());
			BigInteger x = new BigInteger(1
			SXprUtils.skipCloseParenthesis(inputStream);
			return x;
		}

		byte[] keyData = basicData[0];
		byte[] protectedAt = basicData[1];

		InputStream keyIn = new ByteArrayInputStream(keyData);

		SXprUtils.skipOpenParenthesis(keyIn);
		SXprUtils.skipOpenParenthesis(keyIn);

		BigInteger x = readBigInteger("x"

		SXprUtils.skipCloseParenthesis(keyIn);

		if (keyProtectionRemoverFactory instanceof OCBPBEProtectionRemoverFactory) {
			return x;
		}

		SXprUtils.skipOpenParenthesis(keyIn);
		type = SXprUtils.readString(keyIn

		if (!type.equals("hash")) {
			throw new PGPException("hash keyword expected");
		}
		type = SXprUtils.readString(keyIn

		if (!type.equals("sha1")) {
			throw new PGPException("hash keyword expected");
		}

		byte[] hashBytes = SXprUtils.readBytes(keyIn

		SXprUtils.skipCloseParenthesis(keyIn);

		if (digestProvider != null) {
			PGPDigestCalculator digestCalculator = digestProvider
					.get(HashAlgorithmTags.SHA1);

			OutputStream dOut = digestCalculator.getOutputStream();

			dOut.write(Strings.toByteArray("(3:dsa"));
			writeCanonical(dOut
			writeCanonical(dOut
			writeCanonical(dOut
			writeCanonical(dOut
			writeCanonical(dOut

			if (protectedAt != null) {
				dOut.write(protectedAt);
			}

			dOut.write(Strings.toByteArray(")"));

			byte[] check = digestCalculator.getDigest();
			if (!Arrays.constantTimeAreEqual(check
				throw new PGPException(
						"checksum on protected data failed in SExpr");
			}
		}

		return x;
	}

	private BigInteger processElGamalSecretKey(InputStream inputStream
			BigInteger p
			PBEProtectionRemoverFactory keyProtectionRemoverFactory)
			throws IOException
		String type;
		byte[][] basicData = extractData(inputStream
				keyProtectionRemoverFactory);

		if (basicData == null) {
			byte[] nBytes = SXprUtils.readBytes(inputStream
					inputStream.read());
			BigInteger x = new BigInteger(1
			SXprUtils.skipCloseParenthesis(inputStream);
			return x;
		}

		byte[] keyData = basicData[0];
		byte[] protectedAt = basicData[1];

		InputStream keyIn = new ByteArrayInputStream(keyData);

		SXprUtils.skipOpenParenthesis(keyIn);
		SXprUtils.skipOpenParenthesis(keyIn);

		BigInteger x = readBigInteger("x"

		SXprUtils.skipCloseParenthesis(keyIn);

		if (keyProtectionRemoverFactory instanceof OCBPBEProtectionRemoverFactory) {
			return x;
		}

		SXprUtils.skipOpenParenthesis(keyIn);
		type = SXprUtils.readString(keyIn

		if (!type.equals("hash")) {
			throw new PGPException("hash keyword expected");
		}
		type = SXprUtils.readString(keyIn

		if (!type.equals("sha1")) {
			throw new PGPException("hash keyword expected");
		}

		byte[] hashBytes = SXprUtils.readBytes(keyIn

		SXprUtils.skipCloseParenthesis(keyIn);

		if (digestProvider != null) {
			PGPDigestCalculator digestCalculator = digestProvider
					.get(HashAlgorithmTags.SHA1);

			OutputStream dOut = digestCalculator.getOutputStream();

			dOut.write(Strings.toByteArray("(3:elg"));
			writeCanonical(dOut
			writeCanonical(dOut
			writeCanonical(dOut
			writeCanonical(dOut

			if (protectedAt != null) {
				dOut.write(protectedAt);
			}

			dOut.write(Strings.toByteArray(")"));

			byte[] check = digestCalculator.getDigest();
			if (!Arrays.constantTimeAreEqual(check
				throw new PGPException(
						"checksum on protected data failed in SExpr");
			}
		}

		return x;
	}

	private BigInteger processECSecretKey(InputStream inputStream
			String curveID
			PBEProtectionRemoverFactory keyProtectionRemoverFactory)
			throws IOException
		String type;

		byte[][] basicData = extractData(inputStream
				keyProtectionRemoverFactory);

		if (basicData == null) {
			byte[] nBytes = SXprUtils.readBytes(inputStream
					inputStream.read());
			BigInteger d = new BigInteger(1
			SXprUtils.skipCloseParenthesis(inputStream);
			return d;
		}

		byte[] keyData = basicData[0];
		byte[] protectedAt = basicData[1];

		InputStream keyIn = new ByteArrayInputStream(keyData);

		SXprUtils.skipOpenParenthesis(keyIn);
		SXprUtils.skipOpenParenthesis(keyIn);
		BigInteger d = readBigInteger("d"
		SXprUtils.skipCloseParenthesis(keyIn);

		if (keyProtectionRemoverFactory instanceof OCBPBEProtectionRemoverFactory) {
			return d;
		}

		SXprUtils.skipOpenParenthesis(keyIn);

		type = SXprUtils.readString(keyIn

		if (!type.equals("hash")) {
			throw new PGPException("hash keyword expected");
		}
		type = SXprUtils.readString(keyIn

		if (!type.equals("sha1")) {
			throw new PGPException("hash keyword expected");
		}

		byte[] hashBytes = SXprUtils.readBytes(keyIn

		SXprUtils.skipCloseParenthesis(keyIn);

		if (digestProvider != null) {
			PGPDigestCalculator digestCalculator = digestProvider
					.get(HashAlgorithmTags.SHA1);

			OutputStream dOut = digestCalculator.getOutputStream();

			dOut.write(Strings.toByteArray("(3:ecc"));

			dOut.write(Strings.toByteArray("(" + curveID.length() + ":"
					+ curveID + curveName.length() + ":" + curveName + ")"));

			writeCanonical(dOut
			writeCanonical(dOut

			if (protectedAt != null) {
				dOut.write(protectedAt);
			}

			dOut.write(Strings.toByteArray(")"));

			byte[] check = digestCalculator.getDigest();

			if (!Arrays.constantTimeAreEqual(check
				throw new PGPException(
						"checksum on protected data failed in SExpr");
			}
		}

		return d;
	}

	private BigInteger[] processRSASecretKey(InputStream inputStream
			BigInteger n
			PBEProtectionRemoverFactory keyProtectionRemoverFactory)
			throws IOException
		String type;
		byte[][] basicData = extractData(inputStream
				keyProtectionRemoverFactory);

		byte[] keyData;
		byte[] protectedAt = null;

		InputStream keyIn;
		BigInteger d;

		if (basicData == null) {
			keyIn = inputStream;
			byte[] nBytes = SXprUtils.readBytes(inputStream
					inputStream.read());
			d = new BigInteger(1

			SXprUtils.skipCloseParenthesis(inputStream);

		} else {
			keyData = basicData[0];
			protectedAt = basicData[1];

			keyIn = new ByteArrayInputStream(keyData);

			SXprUtils.skipOpenParenthesis(keyIn);
			SXprUtils.skipOpenParenthesis(keyIn);
			d = readBigInteger("d"
		}


		BigInteger p = readBigInteger("p"
		BigInteger q = readBigInteger("q"
		BigInteger u = readBigInteger("u"

		if (basicData == null
				|| keyProtectionRemoverFactory instanceof OCBPBEProtectionRemoverFactory) {
			return new BigInteger[] { d
		}

		SXprUtils.skipCloseParenthesis(keyIn);

		SXprUtils.skipOpenParenthesis(keyIn);
		type = SXprUtils.readString(keyIn

		if (!type.equals("hash")) {
			throw new PGPException("hash keyword expected");
		}
		type = SXprUtils.readString(keyIn

		if (!type.equals("sha1")) {
			throw new PGPException("hash keyword expected");
		}

		byte[] hashBytes = SXprUtils.readBytes(keyIn

		SXprUtils.skipCloseParenthesis(keyIn);

		if (digestProvider != null) {
			PGPDigestCalculator digestCalculator = digestProvider
					.get(HashAlgorithmTags.SHA1);

			OutputStream dOut = digestCalculator.getOutputStream();

			dOut.write(Strings.toByteArray("(3:rsa"));

			writeCanonical(dOut
			writeCanonical(dOut
			writeCanonical(dOut
			writeCanonical(dOut
			writeCanonical(dOut
			writeCanonical(dOut

			if (protectedAt != null) {
				dOut.write(protectedAt);
			}

			dOut.write(Strings.toByteArray(")"));

			byte[] check = digestCalculator.getDigest();

			if (!Arrays.constantTimeAreEqual(check
				throw new PGPException(
						"checksum on protected data failed in SExpr");
			}
		}

		return new BigInteger[] { d
	}

	private void writeCanonical(OutputStream dOut
			throws IOException {
		writeCanonical(dOut
	}

	private void writeCanonical(OutputStream dOut
			throws IOException {
		dOut.write(Strings.toByteArray(
				"(" + label.length() + ":" + label + data.length + ":"));
		dOut.write(data);
		dOut.write(Strings.toByteArray(")"));
	}
}
