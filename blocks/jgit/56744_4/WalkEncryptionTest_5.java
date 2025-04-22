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
