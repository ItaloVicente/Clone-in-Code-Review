
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;

import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.*;

@RunWith(Suite.class)
		WalkEncryptionTest.MinimalSet.class
		WalkEncryptionTest.TestablePBE.class
})
public class WalkEncryptionTest {

	static final Logger logger = Logger.getLogger(Base.class);

	interface Names {


		String TEST_BUCKET = "test.bucket";


		String ENV_ACCESS_KEY = "JGIT_S3_ACCESS_KEY";

		String ENV_SECRET_KEY = "JGIT_S3_SECRET_KEY";

		String ENV_BUCKET_NAME = "JGIT_S3_BUCKET_NAME";


		String ENV_CONFIG_FILE = "JGIT_S3_CONFIG_FILE";


		String SYS_ACCESS_KEY = "jgit.s3.access.key";

		String SYS_SECRET_KEY = "jgit.s3.secret.key";

		String SYS_BUCKET_NAME = "jgit.s3.bucket.name";

		String SYS_CONFIG_FILE = "jgit.s3.config.file";

		String CONFIG_FILE = "jgit-s3-config.properties";

		String HOME_CONFIG_FILE = System.getProperty("user.home")
				+ File.separator + CONFIG_FILE;

		String WORK_CONFIG_FILE = System.getProperty("user.dir")
				+ File.separator + CONFIG_FILE;

		String TEST_CONFIG_FILE = System.getProperty("user.dir")
				+ File.separator + "tst-rsrc" + File.separator + CONFIG_FILE;

	}

	static class Props implements WalkEncryptionTest.Names

		static boolean haveEnvVar(String name) {
			return System.getenv(name) != null;
		}

		static boolean haveEnvVarFile(String name) {
			return haveEnvVar(name) && new File(name).exists();
		}

		static boolean haveSysProp(String name) {
			return System.getProperty(name) != null;
		}

		static boolean haveSysPropFile(String name) {
			return haveSysProp(name) && new File(name).exists();
		}

		static void loadEnvVar(String source
			props.put(target
		}

		static void loadSysProp(String source
				Properties props) {
			props.put(target
		}

		static boolean haveProp(String name
			return props.containsKey(name);
		}

		static boolean checkTestProps(Properties props) {
			return haveProp(ACCESS_KEY
					&& haveProp(TEST_BUCKET
		}

		static Properties fromEnvVars() {
			if (haveEnvVar(ENV_ACCESS_KEY) && haveEnvVar(ENV_SECRET_KEY)
					&& haveEnvVar(ENV_BUCKET_NAME)) {
				Properties props = new Properties();
				loadEnvVar(ENV_ACCESS_KEY
				loadEnvVar(ENV_SECRET_KEY
				loadEnvVar(ENV_BUCKET_NAME
				return props;
			} else {
				return null;
			}
		}

		static Properties fromEnvFile() throws Exception {
			if (haveEnvVarFile(ENV_CONFIG_FILE)) {
				Properties props = new Properties();
				props.load(new FileInputStream(ENV_CONFIG_FILE));
				if (checkTestProps(props)) {
					return props;
				} else {
					throw new Error("Environment config file is incomplete.");
				}
			} else {
				return null;
			}
		}

		static Properties fromSysProps() {
			if (haveSysProp(SYS_ACCESS_KEY) && haveSysProp(SYS_SECRET_KEY)
					&& haveSysProp(SYS_BUCKET_NAME)) {
				Properties props = new Properties();
				loadSysProp(SYS_ACCESS_KEY
				loadSysProp(SYS_SECRET_KEY
				loadSysProp(SYS_BUCKET_NAME
				return props;
			} else {
				return null;
			}
		}

		static Properties fromSysFile() throws Exception {
			if (haveSysPropFile(SYS_CONFIG_FILE)) {
				Properties props = new Properties();
				props.load(new FileInputStream(SYS_CONFIG_FILE));
				if (checkTestProps(props)) {
					return props;
				} else {
					throw new Error("System props config file is incomplete.");
				}
			} else {
				return null;
			}
		}

		static Properties fromConfigFile(String path) throws Exception {
			File file = new File(path);
			if (file.exists()) {
				Properties props = new Properties();
				props.load(new FileInputStream(file));
				if (checkTestProps(props)) {
					return props;
				} else {
					throw new Error("Props config file is incomplete: " + path);
				}
			} else {
				return null;
			}
		}

		static Properties discover() throws Exception {
			Properties props;
			if ((props = fromEnvVars()) != null) {
				logger.debug(
						"Using test properties from environment variables.");
				return props;
			}
			if ((props = fromEnvFile()) != null) {
				logger.debug(
						"Using test properties from environment variable config file.");
				return props;
			}
			if ((props = fromSysProps()) != null) {
				logger.debug("Using test properties from system properties.");
				return props;
			}
			if ((props = fromSysFile()) != null) {
				logger.debug(
						"Using test properties from system property config file.");
				return props;
			}
			if ((props = fromConfigFile(HOME_CONFIG_FILE)) != null) {
				logger.debug(
						"Using test properties from hard coded ${user.home} file.");
				return props;
			}
			if ((props = fromConfigFile(WORK_CONFIG_FILE)) != null) {
				logger.debug(
						"Using test properties from hard coded ${user.dir} file.");
				return props;
			}
			if ((props = fromConfigFile(TEST_CONFIG_FILE)) != null) {
				logger.debug(
						"Using test properties from hard coded ${user.dir} file.");
				return props;
			}
			throw new Error("Can not load test properteis form any source.");
		}

	}

	static class Util {

		static final Charset UTF_8 = Charset.forName("UTF-8");

		static String textRead(File file) throws Exception {
			return new String(Files.readAllBytes(file.toPath())
		}

		static void textWrite(File file
			Files.write(file.toPath()
		}

		static void verifyFileContent(File fileOne
				throws Exception {
			assertTrue(fileOne.length() > 0);
			assertTrue(fileTwo.length() > 0);
			String textOne = textRead(fileOne);
			String textTwo = textRead(fileTwo);
			assertEquals(textOne
		}

		static void folderCreate(String folder) throws Exception {
			File path = new File(folder);
			assertTrue(path.mkdirs());
		}

		static void folderDelete(String folder) throws Exception {
			File path = new File(folder);
			FileUtils.delete(path
					FileUtils.RECURSIVE | FileUtils.SKIP_MISSING);
		}

		static String publicAddress() throws Exception {
			URL url = new URL(service);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(url.openStream()));
			try {
				return in.readLine();
			} finally {
				in.close();
			}
		}

		static List<String> cryptoCipherListPBE() {
			return cryptoCipherList("(PBE).*(WITH).+(AND).+");
		}

		static String securityProviderName(String algorithm) throws Exception {
			return SecretKeyFactory.getInstance(algorithm).getProvider()
					.getName();
		}

		static List<String> cryptoCipherList(String regex) {
			Set<String> source = Security.getAlgorithms("Cipher");
			Set<String> target = new TreeSet<String>();
			for (String algo : source) {
				algo = algo.toUpperCase();
				if (algo.matches(regex)) {
					target.add(algo);
				}
			}
			return new ArrayList<String>(target);
		}

		static boolean isAlgorithmPresent(String algorithm) {
			Set<String> cipherSet = Security.getAlgorithms("Cipher");
			for (String source : cipherSet) {
				String target = algorithm.toUpperCase();
				if (source.equalsIgnoreCase(target)) {
					return true;
				}
			}
			return false;
		}

		static long transferStream(InputStream from
				throws IOException {
			byte[] array = new byte[1 * 1024];
			long total = 0;
			while (true) {
				int count = from.read(array);
				if (count == -1) {
					break;
				}
				into.write(array
				total += count;
			}
			return total;
		}

		static void proxySetup() throws Exception {
			String keyNoProxy = "no_proxy";
			String keyHttpProxy = "http_proxy";
			String keyHttpsProxy = "https_proxy";

			String no_proxy = System.getProperty(keyNoProxy
					System.getenv(keyNoProxy));
			if (no_proxy != null) {
				System.setProperty("http.nonProxyHosts"
				logger.info("Proxy NOT: " + no_proxy);
			}

			String http_proxy = System.getProperty(keyHttpProxy
					System.getenv(keyHttpProxy));
			if (http_proxy != null) {
				URL url = new URL(http_proxy);
				System.setProperty("http.proxyHost"
				System.setProperty("http.proxyPort"
				logger.info("Proxy HTTP: " + http_proxy);
			}

			String https_proxy = System.getProperty(keyHttpsProxy
					System.getenv(keyHttpsProxy));
			if (https_proxy != null) {
				URL url = new URL(https_proxy);
				System.setProperty("https.proxyHost"
				System.setProperty("https.proxyPort"
				logger.info("Proxy HTTPS: " + https_proxy);
			}

			if (no_proxy == null && http_proxy == null && https_proxy == null) {
				logger.info("Proxy not used.");
			}

		}

		static boolean permitLongTests() {
			return isBuildCI() || isProfileActive();
		}

		static boolean isProfileActive() {
			return Boolean.parseBoolean(System.getProperty("jgit.test.long"));
		}

		static boolean isBuildCI() {
			return System.getenv("HUDSON_HOME") != null;
		}

	}

	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public abstract static class Base extends SampleDataRepositoryTestCase {

		static final String JGIT_USER = "tester-" + System.currentTimeMillis();

		static final String JGIT_PASS = "secret-" + System.currentTimeMillis();

		static final String JGIT_CONF_FILE = System.getProperty("user.home")
				+ "/" + JGIT_USER;

		static final String JGIT_REPO_DIR = JGIT_USER + ".jgit";

		static final String JGIT_LOCAL_DIR = System.getProperty("user.dir")
				+ "/target/" + JGIT_REPO_DIR;

		static final String JGIT_REMOTE_DIR = JGIT_REPO_DIR;

		static void configCreate(String algorithm) throws Exception {
			Properties props = Props.discover();
			props.put(AmazonS3.Keys.PASSWORD
			props.put(AmazonS3.Keys.CRYPTO_ALG
			PrintWriter writer = new PrintWriter(JGIT_CONF_FILE);
			props.store(writer
			writer.close();
		}

		static void configDelete() throws Exception {
			File path = new File(JGIT_CONF_FILE);
			FileUtils.delete(path
		}

		static String amazonURI() throws Exception {
			Properties props = Props.discover();
			String bucket = props.getProperty(Names.TEST_BUCKET);
			assertNotNull(bucket);
					+ bucket + "/" + JGIT_REPO_DIR;
		}

		static void remoteCreate() throws Exception {
			Properties props = Props.discover();
			String bucket = props.getProperty(Names.TEST_BUCKET);
			AmazonS3 s3 = new AmazonS3(props);
			String path = JGIT_REMOTE_DIR + "/";
			s3.put(bucket
			logger.debug("remote create: " + JGIT_REMOTE_DIR);
		}

		static void remoteDelete() throws Exception {
			Properties props = Props.discover();
			String bucket = props.getProperty(Names.TEST_BUCKET);
			AmazonS3 s3 = new AmazonS3(props);
			List<String> list = s3.list(bucket
			for (String path : list) {
				path = JGIT_REMOTE_DIR + "/" + path;
				s3.delete(bucket
			}
			logger.debug("remote delete: " + JGIT_REMOTE_DIR);
		}

		static void remoteVerify() throws Exception {
			Properties props = Props.discover();
			String bucket = props.getProperty(Names.TEST_BUCKET);
			AmazonS3 s3 = new AmazonS3(props);
			String file = JGIT_USER + "-" + UUID.randomUUID().toString();
			String path = JGIT_REMOTE_DIR + "/" + file;
			s3.put(bucket
			s3.delete(bucket
		}

		static boolean isAlgorithmPermitted(String algorithm) {
			try {
				WalkEncryption crypto = new WalkEncryption.ObjectEncryptionJetS3tV2(
						algorithm
				verifyCrypto(crypto);
				return true;
			} catch (IOException e) {
			} catch (GeneralSecurityException e) {
			}
		}

		static void verifyCrypto(WalkEncryption crypto) throws IOException {
			String charset = "UTF-8";
			String sourceText = "secret-message Свобода 老子";
			String targetText;
			byte[] cipherText;
			{
				byte[] origin = sourceText.getBytes(charset);
				ByteArrayOutputStream target = new ByteArrayOutputStream();
				OutputStream source = crypto.encrypt(target);
				source.write(origin);
				source.flush();
				source.close();
				cipherText = target.toByteArray();
			}
			{
				InputStream source = new ByteArrayInputStream(cipherText);
				InputStream target = crypto.decrypt(source);
				ByteArrayOutputStream result = new ByteArrayOutputStream();
				transferStream(target
				targetText = result.toString(charset);
			}
			assertEquals(sourceText
		}

		static boolean isAlgorithmTestable(String algorithm) {
			return isAlgorithmPresent(algorithm)
					&& isAlgorithmPermitted(algorithm);
		}

		static void reportAlgorithmStatus(String algorithm) throws Exception {
			final boolean present = isAlgorithmPresent(algorithm);
			final boolean allowed = present && isAlgorithmPermitted(algorithm);
			final String provider = present ? securityProviderName(algorithm)
					: "N/A";
			String status = "Algorithm: " + algorithm + " @ " + provider + "; "
					+ "present/allowed : " + present + "/" + allowed;
			if (allowed) {
				logger.info("Testing " + status);
			} else {
				logger.warn("Missing " + status);
			}
		}

		static boolean isTestConfigPresent() {
			try {
				Props.discover();
				return true;
			} catch (Throwable e) {
				return false;
			}
		}

		static void reportTestConfigPresent() {
			if (isTestConfigPresent()) {
				logger.info("Amazon S3 test configuration is present.");
			} else {
				logger.error(
						"Amazon S3 test configuration is missing
			}
		}

		static void reportPublicAddress() throws Exception {
			logger.info("Public address: " + publicAddress());
		}

		static final String PROVIDER_BC = "org.bouncycastle.jce.provider.BouncyCastleProvider";

		static void loadBouncyCastle() {
			try {
				Class<?> provider = Class.forName(PROVIDER_BC);
				Provider instance = (Provider) provider
						.getConstructor(new Class[] {})
						.newInstance(new Object[] {});
				Security.addProvider(instance);
				logger.info("Loaded " + PROVIDER_BC);
			} catch (Throwable e) {
				logger.warn("Failed to load " + PROVIDER_BC);
			}
		}

		static void reportLongTests() {
			if (permitLongTests()) {
				logger.info("Long running tests are enabled.");
			} else {
				logger.warn("Long running tests are disabled.");
			}
		}

		static final String ALGO_ERROR = "PBKDF2WithHmacSHA1";

		static final String ALGO_JETS3T = "PBEWithMD5AndDES";

		static final String ALGO_MINIMAL_AES = "PBEWithHmacSHA1AndAES_128";

		static final String ALGO_BOUNCY_CASTLE_CBC = "PBEWithSHAAndTwofish-CBC";


		@BeforeClass
		public static void initialize() throws Exception {
			Transport.register(TransportAmazonS3.PROTO_S3);
			proxySetup();
			reportLongTests();
			reportPublicAddress();
			reportTestConfigPresent();
			loadBouncyCastle();
			if (isTestConfigPresent()) {
				remoteCreate();
			}
		}

		@AfterClass
		public static void terminate() throws Exception {
			configDelete();
			folderDelete(JGIT_LOCAL_DIR);
			if (isTestConfigPresent()) {
				remoteDelete();
			}
		}

		@Before
		@Override
		public void setUp() throws Exception {
			super.setUp();
		}

		@After
		@Override
		public void tearDown() throws Exception {
			super.tearDown();
		}

		void cryptoTestIfCan(String algorithm) throws Exception {
			reportAlgorithmStatus(algorithm);
			assumeTrue(isTestConfigPresent());
			assumeTrue(isAlgorithmTestable(algorithm));
			cryptoTest(algorithm);
		}

		void cryptoTest(String algorithm) throws Exception {

			remoteDelete();
			configCreate(algorithm);
			folderDelete(JGIT_LOCAL_DIR);

			String uri = amazonURI();

			File dirTwo = new File(JGIT_LOCAL_DIR);

			String nameDynamic = JGIT_USER + "-" + UUID.randomUUID().toString();

			String remote = "remote";
			RefSpec specs = new RefSpec("refs/heads/master:refs/heads/master");


				StoredConfig config = db.getConfig();
				RemoteConfig remoteConfig = new RemoteConfig(config
				remoteConfig.addURI(new URIish(uri));
				remoteConfig.update(config);
				config.save();

				Git git = Git.open(dirOne);
				git.checkout().setName("master").call();
				git.push().setRemote(remote).setRefSpecs(specs).call();
				git.close();

				File fileStatic = new File(dirOne
				assertTrue("Provided by setup"

			}


				File fileStatic = new File(dirTwo
				assertFalse("Not Provided by setup"

				Git git = Git.cloneRepository().setURI(uri).setDirectory(dirTwo)
						.call();
				git.close();

				assertTrue("Provided by clone"
			}

				File fileOne = new File(dirOne
				File fileTwo = new File(dirTwo
				verifyFileContent(fileOne
			}


				File fileDynamic = new File(dirOne
				assertFalse("Not Provided by setup"
				FileUtils.createNewFile(fileDynamic);
				textWrite(fileDynamic
				assertTrue("Provided by create"
				assertTrue("Need content to encrypt"

				Git git = Git.open(dirOne);
				git.add().addFilepattern(nameDynamic).call();
				git.commit().setMessage(nameDynamic).call();
				git.push().setRemote(remote).setRefSpecs(specs).call();
				git.close();

			}


				File fileDynamic = new File(dirTwo
				assertFalse("Not Provided by setup"

				Git git = Git.open(dirTwo);
				git.pull().call();
				git.close();

				assertTrue("Provided by pull"
			}

				File fileOne = new File(dirOne
				File fileTwo = new File(dirTwo
				verifyFileContent(fileOne
			}

		}

	}

	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public static class MinimalSet extends Base {

		@Test
		public void test_A1_ValidURI() throws Exception {
			assumeTrue(isTestConfigPresent());
			URIish uri = new URIish(amazonURI());
			assertTrue("uri=" + uri
		}

		@Test(expected = Exception.class)
		public void test_A2_CryptoError() throws Exception {
			assumeTrue(isTestConfigPresent());
			cryptoTest(ALGO_ERROR);
		}

		@Test
		public void test_A3_CryptoJetS3tDefault() throws Exception {
			cryptoTestIfCan(ALGO_JETS3T);
		}

		@Test
		public void test_A4_CryptoMinimalAES() throws Exception {
			cryptoTestIfCan(ALGO_MINIMAL_AES);
		}

		@Test
		public void test_A5_CryptoBouncyCastleCBC() throws Exception {
			cryptoTestIfCan(ALGO_BOUNCY_CASTLE_CBC);
		}

	}

	@RunWith(Parameterized.class)
	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public static class TestablePBE extends Base {

		@Parameterized.Parameters
		public static Collection algorimthmList() {
			List<String> source = cryptoCipherListPBE();
			List<Object[]> target = new ArrayList<Object[]>();
			for (String name : source) {
				target.add(new Object[] { name });
			}
			return target;
		}

		final String algorithm;

		public TestablePBE(String algorithm) {
			this.algorithm = algorithm;
		}

		public void test_B1_Crypto() throws Exception {
			assumeTrue(permitLongTests());
			cryptoTestIfCan(algorithm);
		}

	}

}
