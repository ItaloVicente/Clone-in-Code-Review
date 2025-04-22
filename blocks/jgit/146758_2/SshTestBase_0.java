package org.eclipse.jgit.transport.sshd;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.apache.sshd.common.config.keys.loader.KeyPairResourceParser;
import org.apache.sshd.common.config.keys.loader.openssh.OpenSSHKeyPairResourceParser;
import org.apache.sshd.common.config.keys.loader.pem.PEMResourceParserUtils;
import org.apache.sshd.common.config.keys.loader.putty.PuttyKeyUtils;
import org.apache.sshd.common.util.security.SecurityUtils;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.ssh.SshTestHarness;
import org.eclipse.jgit.util.FS;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class PuttyKeysTest extends SshTestHarness {

	@DataPoints
			"putty_rsa_2048.ppk"
			"putty_ed25519.ppk"
			"putty_rsa_2048_testpass.ppk"
			"putty_ed25519_testpass.ppk" };

	private static KeyPairResourceParser initialParser;

	@BeforeClass
	public static void setupPuttyTests() {
		initialParser = SecurityUtils.getKeyPairResourceParser();
		SecurityUtils.setKeyPairResourceParser(
				KeyPairResourceParser.aggregate(PEMResourceParserUtils.PROXY
						OpenSSHKeyPairResourceParser.INSTANCE
						PuttyKeyUtils.DEFAULT_INSTANCE));

	}

	@AfterClass
	public static void teardownPuttyTests() {
		SecurityUtils.setKeyPairResourceParser(initialParser);
	}

	@Override
	protected SshSessionFactory createSessionFactory() {
		SshdSessionFactory result = new SshdSessionFactory(new JGitKeyCache()
				null);
		result.setHomeDirectory(FS.DETECTED.userHome());
		result.setSshDirectory(sshDir);
		return result;
	}

	@Override
	protected void installConfig(String... config) {
		File configFile = new File(sshDir
		if (config != null) {
			try {
				Files.write(configFile.toPath()
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}

	@Theory
	public void testPuttyKeys(String keyName) throws Exception {
		runKeyTest(keyName);
	}
}
