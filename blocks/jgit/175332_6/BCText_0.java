package org.eclipse.jgit.gpg.bc.internal.keys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.Iterator;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.PGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SecretKeysTest {

	@BeforeClass
	public static void ensureBC() {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}

	private static volatile Boolean haveOCB;

	private static boolean ocbAvailable() {
		Boolean haveIt = haveOCB;
		if (haveIt != null) {
			return haveIt.booleanValue();
		}
		try {
			if (c == null) {
				haveOCB = Boolean.FALSE;
				return false;
			}
		} catch (NoClassDefFoundError | Exception e) {
			haveOCB = Boolean.FALSE;
			return false;
		}
		haveOCB = Boolean.TRUE;
		return true;
	}

	private static class TestData {

		final String name;

		final boolean encrypted;

		TestData(String name
			this.name = name;
			this.encrypted = encrypted;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	@Parameters(name = "{0}")
	public static TestData[] initTestData() {
		return new TestData[] {
				new TestData("2FB05DBB70FC07CB84C13431F640CA6CEA1DBF8A"
				new TestData("66CCECEC2AB46A9735B10FEC54EDF9FD0F77BAF9"
				new TestData("F727FAB884DA3BD402B6E0F5472E108D21033124"
				new TestData("faked"
	}

	private static byte[] readTestKey(String filename) throws Exception {
		try (InputStream in = new BufferedInputStream(
				SecretKeysTest.class.getResourceAsStream(filename))) {
			return SecretKeys.keyFromNameValueFormat(in);
		}
	}

	private static PGPPublicKey readAsc(InputStream in)
			throws IOException
		PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(
			PGPUtil.getDecoderStream(in)

		Iterator<PGPPublicKeyRing> keyRings = pgpPub.getKeyRings();
		while (keyRings.hasNext()) {
			PGPPublicKeyRing keyRing = keyRings.next();

			Iterator<PGPPublicKey> keys = keyRing.getPublicKeys();
			if (keys.hasNext()) {
				return keys.next();
			}
		}
		return null;
	}

	@Parameter
	public TestData data;

	@Test
	public void testKeyRead() throws Exception {
		byte[] bytes = readTestKey(data.name + ".key");
		assertEquals('('
		assertEquals(')'
		try (InputStream pubIn = this.getClass()
				.getResourceAsStream(data.name + ".asc")) {
			if (pubIn != null) {
				PGPPublicKey publicKey = readAsc(pubIn);
				PGPDigestCalculatorProvider calculatorProvider = new JcaPGPDigestCalculatorProviderBuilder()
						.build();
				try (InputStream in = new BufferedInputStream(this.getClass()
						.getResourceAsStream(data.name + ".key"))) {
					PGPSecretKey secretKey = SecretKeys.readSecretKey(in
							calculatorProvider
							publicKey);
					assertNotNull(secretKey);
				} catch (PGPException e) {
					assertTrue(e.getMessage().contains("OCB"));
					assertTrue(data.encrypted);
					assertFalse(ocbAvailable());
				}
			}
		}
	}

}
