		public void test_V1_Java7_GIT() throws Exception {
			assumeTrue(isTestConfigPresent());
			Properties props = new Properties();
			props.put(AmazonS3.Keys.CRYPTO_ALG
			props.put(AmazonS3.Keys.CRYPTO_VER
			props.put(AmazonS3.Keys.PASSWORD
			cryptoTestIfCan(props);
