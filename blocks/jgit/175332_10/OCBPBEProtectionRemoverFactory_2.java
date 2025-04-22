package org.eclipse.jgit.gpg.bc.internal.keys;

import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.PBEProtectionRemoverFactory;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.PGPDigestCalculatorProvider;
import org.bouncycastle.util.Arrays;
import org.eclipse.jgit.gpg.bc.internal.BCText;

class OCBPBEProtectionRemoverFactory
		implements PBEProtectionRemoverFactory {

	private final PGPDigestCalculatorProvider calculatorProvider;

	private final char[] passphrase;

	private final byte[] aad;

	OCBPBEProtectionRemoverFactory(char[] passphrase
			PGPDigestCalculatorProvider calculatorProvider
		this.calculatorProvider = calculatorProvider;
		this.passphrase = passphrase;
		this.aad = aad;
	}

	@Override
	public PBESecretKeyDecryptor createDecryptor(String protection)
			throws PGPException {
		return new PBESecretKeyDecryptor(passphrase

			@Override
			public byte[] recoverKeyData(int encAlgorithm
					byte[] iv
					int encryptedLength) throws PGPException {
				String algorithmName = PGPUtil
						.getSymmetricCipherName(encAlgorithm);
				byte[] decrypted = null;
				try {
					Cipher c = Cipher
					SecretKey secretKey = new SecretKeySpec(key
					c.init(Cipher.DECRYPT_MODE
							new IvParameterSpec(iv));
					c.updateAAD(aad);
					decrypted = new byte[c.getOutputSize(encryptedLength)];
					int decryptedLength = c.update(encrypted
							encryptedLength
					decryptedLength += c.doFinal(decrypted
					if (decryptedLength != decrypted.length) {
						throw new PGPException(MessageFormat.format(
								BCText.get().cryptWrongDecryptedLength
								Integer.valueOf(decryptedLength)
								Integer.valueOf(decrypted.length)));
					}
					byte[] result = decrypted;
					return result;
				} catch (NoClassDefFoundError e) {
					String msg = MessageFormat.format(
							BCText.get().gpgNoSuchAlgorithm
					throw new PGPException(msg
							new NoSuchAlgorithmException(msg
				} catch (PGPException e) {
					throw e;
				} catch (Exception e) {
					throw new PGPException(
							MessageFormat.format(BCText.get().cryptCipherError
									e.getLocalizedMessage())
							e);
				} finally {
					if (decrypted != null) {
						Arrays.fill(decrypted
					}
				}
			}
		};
	}
}
