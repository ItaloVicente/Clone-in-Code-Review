		@Parameters(name = "Profile: {0}   Version: {1}")
		public static Collection<Object[]> argsList() {
			List<String> algorithmList = new ArrayList<String>();
			algorithmList.addAll(cryptoCipherListPBE());

			List<String> versionList = new ArrayList<String>();
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
			List<String> algorithmList = new ArrayList<String>();
			algorithmList.addAll(cryptoCipherListTrans());

			List<String> versionList = new ArrayList<String>();
			versionList.add("1");

			return product(algorithmList
