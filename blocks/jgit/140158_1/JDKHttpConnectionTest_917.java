
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.cryptoCipherListPBE;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.cryptoCipherListTrans;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.folderDelete;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.permitLongTests;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.policySetup;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.product;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.proxySetup;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.publicAddress;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.reportPolicy;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.securityProviderName;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.textWrite;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.transferStream;
import static org.eclipse.jgit.transport.WalkEncryptionTest.Util.verifyFileContent;
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
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;

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
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Suite.class)
		WalkEncryptionTest.Required.class
		WalkEncryptionTest.MinimalSet.class
		WalkEncryptionTest.TestablePBE.class
		WalkEncryptionTest.TestableTransformation.class
})
public class WalkEncryptionTest {

	static final Logger logger = LoggerFactory.getLogger(WalkEncryptionTest.class);

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
						"Using test properties from hard coded ${project.source} file.");
				return props;
			}
			throw new Error("Can not load test properties form any source.");
		}

	}

	static class Util {

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
			try {
				URL url = new URL(service);
				URLConnection c = url.openConnection();
				c.setConnectTimeout(500);
				c.setReadTimeout(500);
				try (BufferedReader reader = new BufferedReader(
						new InputStreamReader(c.getInputStream()
					return reader.readLine();
				}
			} catch (UnknownHostException | SocketTimeoutException e) {
						+ " determine public address";
			}
		}

		static List<String> cryptoCipherListPBE() {
			return cryptoCipherList(WalkEncryption.Vals.REGEX_PBE);
		}

		static List<String> cryptoCipherListTrans() {
			return cryptoCipherList(WalkEncryption.Vals.REGEX_TRANS);
		}

		static String securityProviderName(String algorithm) throws Exception {
			return SecretKeyFactory.getInstance(algorithm).getProvider()
					.getName();
		}

		static List<String> cryptoCipherList(String regex) {
			Set<String> source = Security.getAlgorithms("Cipher");
			Set<String> target = new TreeSet<>();
			for (String algo : source) {
				algo = algo.toUpperCase(Locale.ROOT);
				if (algo.matches(regex)) {
					target.add(algo);
				}
			}
			return new ArrayList<>(target);
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

		static void policySetup(boolean restrictedOn) {
			try {
				java.lang.reflect.Field isRestricted = Class
						.forName("javax.crypto.JceSecurity")
						.getDeclaredField("isRestricted");
				isRestricted.setAccessible(true);
				isRestricted.set(null
			} catch (Throwable e) {
				logger.info(
						"Could not setup JCE security policy restrictions.");
			}
		}

		static void reportPolicy() {
			try {
				java.lang.reflect.Field isRestricted = Class
						.forName("javax.crypto.JceSecurity")
						.getDeclaredField("isRestricted");
				isRestricted.setAccessible(true);
				logger.info("JCE security policy restricted="
						+ isRestricted.get(null));
			} catch (Throwable e) {
				logger.info(
						"Could not report JCE security policy restrictions.");
			}
		}

		static List<Object[]> product(List<String> one
			List<Object[]> result = new ArrayList<>();
			for (String s1 : one) {
				for (String s2 : two) {
					result.add(new Object[] { s1
				}
			}
			return result;
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
			try (PrintWriter writer = new PrintWriter(JGIT_CONF_FILE
					UTF_8.name())) {
				props.store(writer
			}
		}

		static void configCreate(Properties source) throws Exception {
			Properties target = Props.discover();
			target.putAll(source);
			try (PrintWriter writer = new PrintWriter(JGIT_CONF_FILE
					UTF_8.name())) {
				target.store(writer
			}
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

		static boolean isAlgorithmPresent(String algorithm) {
			Set<String> cipherSet = Security.getAlgorithms("Cipher");
			for (String source : cipherSet) {
				String target = algorithm.toUpperCase(Locale.ROOT);
				if (source.equalsIgnoreCase(target)) {
					return true;
				}
			}
			return false;
		}

		static boolean isAlgorithmPresent(Properties props) {
			String profile = props.getProperty(AmazonS3.Keys.CRYPTO_ALG);
			String version = props.getProperty(AmazonS3.Keys.CRYPTO_VER
					WalkEncryption.Vals.DEFAULT_VERS);
			String cryptoAlgo;
			String keyAlgo;
			switch (version) {
			case WalkEncryption.Vals.DEFAULT_VERS:
			case WalkEncryption.JGitV1.VERSION:
				cryptoAlgo = profile;
				keyAlgo = profile;
				break;
			case WalkEncryption.JGitV2.VERSION:
				cryptoAlgo = props
						.getProperty(profile + WalkEncryption.Keys.X_ALGO);
				keyAlgo = props
						.getProperty(profile + WalkEncryption.Keys.X_KEY_ALGO);
				break;
			default:
				return false;
			}
			try {
				InsecureCipherFactory.create(cryptoAlgo);
				SecretKeyFactory.getInstance(keyAlgo);
				return true;
			} catch (Throwable e) {
				return false;
			}
		}

		static boolean isAlgorithmAllowed(String algorithm) {
			try {
				WalkEncryption crypto = new WalkEncryption.JetS3tV2(
						algorithm
				verifyCrypto(crypto);
				return true;
			} catch (IOException e) {
			} catch (GeneralSecurityException e) {
			}
		}

		static boolean isAlgorithmAllowed(Properties props) {
			try {
				WalkEncryption.instance(props);
				return true;
			} catch (GeneralSecurityException e) {
				return false;
			}
		}

		static void verifyCrypto(WalkEncryption crypto) throws IOException {
			String charset = "UTF-8";
			String sourceText = "secret-message Ð¡Ð²Ð¾Ð±Ð¾Ð´Ð° èå­";
			String targetText;
			byte[] cipherText;
			{
				byte[] origin = sourceText.getBytes(charset);
				ByteArrayOutputStream target = new ByteArrayOutputStream();
				try (OutputStream source = crypto.encrypt(target)) {
					source.write(origin);
					source.flush();
				}
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
					&& isAlgorithmAllowed(algorithm);
		}

		static boolean isAlgorithmTestable(Properties props) {
			return isAlgorithmPresent(props) && isAlgorithmAllowed(props);
		}

		static void reportAlgorithmStatus(String algorithm) throws Exception {
			final boolean present = isAlgorithmPresent(algorithm);
			final boolean allowed = present && isAlgorithmAllowed(algorithm);
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

		static void reportAlgorithmStatus(Properties props) throws Exception {
			final boolean present = isAlgorithmPresent(props);
			final boolean allowed = present && isAlgorithmAllowed(props);

			String profile = props.getProperty(AmazonS3.Keys.CRYPTO_ALG);
			String version = props.getProperty(AmazonS3.Keys.CRYPTO_VER);

			StringBuilder status = new StringBuilder();
			status.append(" Version: " + version);
			status.append(" Profile: " + profile);
			status.append(" Present: " + present);
			status.append(" Allowed: " + allowed);

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
			reportPolicy();
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

		void cryptoTestIfCan(Properties props) throws Exception {
			reportAlgorithmStatus(props);
			assumeTrue(isTestConfigPresent());
			assumeTrue(isAlgorithmTestable(props));
			cryptoTest(props);
		}

		void cryptoTest(Properties props) throws Exception {

			remoteDelete();
			configCreate(props);
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

				try (Git git = Git.open(dirOne)) {
					git.checkout().setName("master").call();
					git.push().setRemote(remote).setRefSpecs(specs).call();
				}

				File fileStatic = new File(dirOne
				assertTrue("Provided by setup"

			}


				File fileStatic = new File(dirTwo
				assertFalse("Not Provided by setup"

				try (Git git = Git.cloneRepository().setURI(uri)
						.setDirectory(dirTwo).call()) {
					assertTrue("Provided by clone"
				}

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

				try (Git git = Git.open(dirOne)) {
					git.add().addFilepattern(nameDynamic).call();
					git.commit().setMessage(nameDynamic).call();
					git.push().setRemote(remote).setRefSpecs(specs).call();
				}

			}


				File fileDynamic = new File(dirTwo
				assertFalse("Not Provided by setup"

				try (Git git = Git.open(dirTwo)) {
					git.pull().call();
				}

				assertTrue("Provided by pull"
			}

				File fileOne = new File(dirOne
				File fileTwo = new File(dirTwo
				verifyFileContent(fileOne
			}

		}

	}

	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public static class Required extends Base {

		@Test
		public void test_A1_ValidURI() throws Exception {
			assumeTrue(isTestConfigPresent());
			URIish uri = new URIish(amazonURI());
			assertTrue("uri=" + uri
		}

		@Test(expected = Exception.class)
		public void test_A2_CryptoError() throws Exception {
			assumeTrue(isTestConfigPresent());
			Properties props = new Properties();
			props.put(AmazonS3.Keys.CRYPTO_ALG
			props.put(AmazonS3.Keys.PASSWORD
			cryptoTest(props);
		}

	}

	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public static class MinimalSet extends Base {

		@Test
		public void test_V0_Java7_JET() throws Exception {
			assumeTrue(isTestConfigPresent());
			Properties props = new Properties();
			props.put(AmazonS3.Keys.CRYPTO_ALG
			props.put(AmazonS3.Keys.PASSWORD
			cryptoTestIfCan(props);
		}

		@Test
		public void test_V1_Java7_GIT() throws Exception {
			assumeTrue(isTestConfigPresent());
			Properties props = new Properties();
			props.put(AmazonS3.Keys.CRYPTO_ALG
			props.put(AmazonS3.Keys.CRYPTO_VER
			props.put(AmazonS3.Keys.PASSWORD
			cryptoTestIfCan(props);
		}

		@Test
		public void test_V2_Java7_AES() throws Exception {
			assumeTrue(isTestConfigPresent());
			String profile = "AES/CBC/PKCS5Padding+PBKDF2WithHmacSHA1";
			Properties props = new Properties();
			props.put(AmazonS3.Keys.CRYPTO_ALG
			props.put(AmazonS3.Keys.CRYPTO_VER
			props.put(AmazonS3.Keys.PASSWORD
			props.put(profile + WalkEncryption.Keys.X_ALGO
			props.put(profile + WalkEncryption.Keys.X_KEY_ALGO
			props.put(profile + WalkEncryption.Keys.X_KEY_SIZE
			props.put(profile + WalkEncryption.Keys.X_KEY_ITER
			props.put(profile + WalkEncryption.Keys.X_KEY_SALT
			cryptoTestIfCan(props);
		}

		@Test
		public void test_V2_Java8_PBE_AES() throws Exception {
			assumeTrue(isTestConfigPresent());
			String profile = "PBEWithHmacSHA512AndAES_256";
			Properties props = new Properties();
			props.put(AmazonS3.Keys.CRYPTO_ALG
			props.put(AmazonS3.Keys.CRYPTO_VER
			props.put(AmazonS3.Keys.PASSWORD
			props.put(profile + WalkEncryption.Keys.X_ALGO
			props.put(profile + WalkEncryption.Keys.X_KEY_ALGO
			props.put(profile + WalkEncryption.Keys.X_KEY_SIZE
			props.put(profile + WalkEncryption.Keys.X_KEY_ITER
			props.put(profile + WalkEncryption.Keys.X_KEY_SALT
			policySetup(false);
			cryptoTestIfCan(props);
		}

	}

	@RunWith(Parameterized.class)
	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public static class TestablePBE extends Base {

		@Parameters(name = "Profile: {0}   Version: {1}")
		public static Collection<Object[]> argsList() {
			List<String> algorithmList = new ArrayList<>();
			algorithmList.addAll(cryptoCipherListPBE());

			List<String> versionList = new ArrayList<>();
			versionList.add("0");
			versionList.add("1");

			return product(algorithmList
		}

		final String profile;

		final String version;

		final String password = JGIT_PASS;

		public TestablePBE(String profile
			this.profile = profile;
			this.version = version;
		}

		@Test
		public void testCrypto() throws Exception {
			assumeTrue(permitLongTests());
			Properties props = new Properties();
			props.put(AmazonS3.Keys.CRYPTO_ALG
			props.put(AmazonS3.Keys.CRYPTO_VER
			props.put(AmazonS3.Keys.PASSWORD
			cryptoTestIfCan(props);
		}

	}

	@RunWith(Parameterized.class)
	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public static class TestableTransformation extends Base {

		@Parameters(name = "Profile: {0}   Version: {1}")
		public static Collection<Object[]> argsList() {
			List<String> algorithmList = new ArrayList<>();
			algorithmList.addAll(cryptoCipherListTrans());

			List<String> versionList = new ArrayList<>();
			versionList.add("1");

			return product(algorithmList
		}

		final String profile;

		final String version;

		final String password = JGIT_PASS;

		public TestableTransformation(String profile
			this.profile = profile;
			this.version = version;
		}

		@Test
		public void testCrypto() throws Exception {
			assumeTrue(permitLongTests());
			Properties props = new Properties();
			props.put(AmazonS3.Keys.CRYPTO_ALG
			props.put(AmazonS3.Keys.CRYPTO_VER
			props.put(AmazonS3.Keys.PASSWORD
			cryptoTestIfCan(props);
		}

	}

}
