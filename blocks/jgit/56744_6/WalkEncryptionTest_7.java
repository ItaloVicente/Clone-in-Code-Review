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
