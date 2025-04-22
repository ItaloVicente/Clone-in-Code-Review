
package org.eclipse.jgit.transport;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
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
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import static org.junit.Assume.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WalkEncryptionTest extends SampleDataRepositoryTestCase {

	static final Logger logger = Logger.getLogger(WalkEncryptionTest.class);

	interface TestKeys {


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

	static class TestProps implements TestKeys

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

	static final Charset UTF_8 = Charset.forName("UTF-8");

	static String textRead(File file) throws Exception {
		return new String(Files.readAllBytes(file.toPath())
	}

	static void textWrite(File file
		Files.write(file.toPath()
	}

	static void verifyFileContent(File fileOne
		assertTrue(fileOne.length() > 0);
		assertTrue(fileTwo.length() > 0);
		String textOne = textRead(fileOne);
		String textTwo = textRead(fileTwo);
		assertEquals(textOne
	}

	static final String JGIT_USER = "tester-" + System.currentTimeMillis();

	static final String JGIT_PASS = "secret-" + System.currentTimeMillis();

	static final String JGIT_CONF_FILE = System.getProperty("user.home") + "/"
			+ JGIT_USER;

	static final String JGIT_REPO_DIR = JGIT_USER + ".jgit";

	static final String JGIT_LOCAL_DIR = System.getProperty("user.dir")
			+ "/target/" + JGIT_REPO_DIR;

	static final String JGIT_REMOTE_DIR = JGIT_REPO_DIR;

	static void configCreate(String algorithm) throws Exception {
		Properties props = TestProps.discover();
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
		Properties props = TestProps.discover();
		String bucket = props.getProperty(TestKeys.TEST_BUCKET);
		assertNotNull(bucket);
				+ "/" + JGIT_REPO_DIR;
	}

	static void folderCreate(String folder) throws Exception {
		File path = new File(folder);
		assertTrue(path.mkdirs());
	}

	static void folderDelete(String folder) throws Exception {
		File path = new File(folder);
		FileUtils.delete(path
	}

	static void remoteCreate() throws Exception {
		Properties props = TestProps.discover();
		String bucket = props.getProperty(TestKeys.TEST_BUCKET);
		AmazonS3 s3 = new AmazonS3(props);
		String path = JGIT_REMOTE_DIR + "/";
		s3.put(bucket
		logger.debug("remote create: " + JGIT_REMOTE_DIR);
	}

	static void remoteDelete() throws Exception {
		Properties props = TestProps.discover();
		String bucket = props.getProperty(TestKeys.TEST_BUCKET);
		AmazonS3 s3 = new AmazonS3(props);
		List<String> list = s3.list(bucket
		for (String path : list) {
			path = JGIT_REMOTE_DIR + "/" + path;
			s3.delete(bucket
		}
		logger.debug("remote delete: " + JGIT_REMOTE_DIR);
	}

	static List<String> cryptoCipherListPBE() {
		return cryptoCipherList("(PBE).*(WITH).+(AND).+");
	}

	static String securityProviderName(String algorithm) throws Exception {
		return SecretKeyFactory.getInstance(algorithm).getProvider().getName();
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

	static boolean isAlgorithmPresent(String target) {
		Set<String> set = Security.getAlgorithms("Cipher");
		for (String source : set) {
			if (source.equalsIgnoreCase(target)) {
				return true;
			}
		}
		return false;
	}

	static boolean isTestConfigPresent() {
		try {
			TestProps.discover();
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

	static boolean permitLongTests() {
		return Boolean.parseBoolean(System.getProperty("jgit.test.long"));
	}

	static void reportLongTests() {
		if (permitLongTests()) {
			logger.info("Long running tests are enabled.");
		} else {
			logger.warn("Long running tests are disabled.");
		}
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

	static final String ALGO_ERROR = "PBKDF2WithHmacSHA1";

	static final String ALGO_JETS3T = "PBEWithMD5AndDES";

	static final String ALGO_MINIMAL_AES = "PBEWithHmacSHA1AndAES_128";

	static final String ALGO_BOUNCY_CASTLE_CBC = "PBEWithSHAAndTwofish-CBC";


	@BeforeClass
	public static void initialize() throws Exception {
		Transport.register(TransportAmazonS3.PROTO_S3);
		proxySetup();
		loadBouncyCastle();
		if (isTestConfigPresent()) {
			remoteCreate();
		}
		reportLongTests();
		reportPublicAddress();
		reportTestConfigPresent();
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
		assumeTrue(isTestConfigPresent());
		cryptoTestIfAlgorithmIsPresent(ALGO_JETS3T);
	}

	@Test
	public void test_A4_CryptoMinimalAES() throws Exception {
		assumeTrue(isTestConfigPresent());
		cryptoTestIfAlgorithmIsPresent(ALGO_MINIMAL_AES);
	}

	@Test
	public void test_A5_CryptoBouncyCastleCBC() throws Exception {
		assumeTrue(isTestConfigPresent());
		cryptoTestIfAlgorithmIsPresent(ALGO_BOUNCY_CASTLE_CBC);
	}

	public void test_B1_CryptoAllPBE() throws Exception {
		assumeTrue(permitLongTests());
		assumeTrue(isTestConfigPresent());
		List<String> list = cryptoCipherListPBE();
		for (String algo : list) {
			try {
				setUp();
				cryptoTest(algo);
			} finally {
				tearDown();
			}
		}
	}

	void cryptoTestIfAlgorithmIsPresent(String algorithm) throws Exception {
		if (isAlgorithmPresent(algorithm)) {
			cryptoTest(algorithm);
		} else {
			logger.warn("Missing algorithm: " + algorithm);
		}
	}

	void cryptoTest(String algorithm) throws Exception {

		logger.info("Testing algorithm: " + algorithm + " / "
				+ securityProviderName(algorithm));

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
