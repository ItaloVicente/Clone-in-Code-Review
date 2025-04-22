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

