
	interface Keys {



	}

	interface Vals {
		String DEFAULT_ALGO = JetS3tV2.ALGORITHM;
		String DEFAULT_KEY_ALGO = JetS3tV2.ALGORITHM;
		String DEFAULT_KEY_SIZE = Integer.toString(JetS3tV2.KEY_SIZE);
		String DEFAULT_KEY_ITER = Integer.toString(JetS3tV2.ITERATIONS);
		String DEFAULT_KEY_SALT = DatatypeConverter.printHexBinary(JetS3tV2.SALT);




	}

	static GeneralSecurityException securityError(String message) {
		return new GeneralSecurityException(
				MessageFormat.format(JGitText.get().encryptionError
	}

	static abstract class SymmetricEncryption extends WalkEncryption
			implements Keys

		final String profile;

		final String version;

		final String cipherAlgo;

		final String paramsAlgo;

		final SecretKey secretKey;

		SymmetricEncryption(Properties props) throws GeneralSecurityException {

			profile = props.getProperty(AmazonS3.Keys.CRYPTO_ALG);
			version = props.getProperty(AmazonS3.Keys.CRYPTO_VER);
			String pass = props.getProperty(AmazonS3.Keys.PASSWORD);

			cipherAlgo = props.getProperty(profile + X_ALGO

			String keyAlgo = props.getProperty(profile + X_KEY_ALGO
			String keySize = props.getProperty(profile + X_KEY_SIZE
			String keyIter = props.getProperty(profile + X_KEY_ITER
			String keySalt = props.getProperty(profile + X_KEY_SALT

			Cipher cipher = Cipher.getInstance(cipherAlgo);

			SecretKeyFactory factory = SecretKeyFactory.getInstance(keyAlgo);

			final int size;
			try {
				size = Integer.parseInt(keySize);
			} catch (Exception e) {
				throw securityError(X_KEY_SIZE + EMPTY + keySize);
			}

			final int iter;
			try {
				iter = Integer.parseInt(keyIter);
			} catch (Exception e) {
				throw securityError(X_KEY_ITER + EMPTY + keyIter);
			}

			final byte[] salt;
			try {
				salt = DatatypeConverter
						.parseHexBinary(keySalt.replaceAll(REGEX_WS
			} catch (Exception e) {
				throw securityError(X_KEY_SALT + EMPTY + keySalt);
			}

			KeySpec keySpec = new PBEKeySpec(pass.toCharArray()

			SecretKey keyBase = factory.generateSecret(keySpec);

			String name = cipherAlgo.toUpperCase();
			Matcher matcherPBE = Pattern.compile(REGEX_PBE).matcher(name);
			Matcher matcherTrans = Pattern.compile(REGEX_TRANS).matcher(name);
			if (matcherPBE.matches()) {
				paramsAlgo = cipherAlgo;
				secretKey = keyBase;
			} else if (matcherTrans.find()) {
				paramsAlgo = matcherTrans.group(1);
				secretKey = new SecretKeySpec(keyBase.getEncoded()
			} else {
				throw new GeneralSecurityException(MessageFormat.format(
						JGitText.get().unsupportedEncryptionAlgorithm
						cipherAlgo));
			}

			cipher.init(Cipher.ENCRYPT_MODE
			cipher.doFinal();

		}

		volatile String context;

		@Override
		OutputStream encrypt(OutputStream output) throws IOException {
			try {
				Cipher cipher = Cipher.getInstance(cipherAlgo);
				cipher.init(Cipher.ENCRYPT_MODE
				AlgorithmParameters params = cipher.getParameters();
				if (params == null) {
					context = EMPTY;
				} else {
					context = Base64.encodeBytes(params.getEncoded());
				}
				return new CipherOutputStream(output
			} catch (Exception e) {
				throw error(e);
			}
		}

		@Override
		void request(HttpURLConnection conn
			conn.setRequestProperty(prefix + JGIT_PROFILE
			conn.setRequestProperty(prefix + JGIT_VERSION
			conn.setRequestProperty(prefix + JGIT_CONTEXT
		}

		volatile Cipher decryptCipher;

		@Override
		void validate(HttpURLConnection conn
				throws IOException {
			String prof = conn.getHeaderField(prefix + JGIT_PROFILE);
			String vers = conn.getHeaderField(prefix + JGIT_VERSION);
			String cont = conn.getHeaderField(prefix + JGIT_CONTEXT);

			if (prof == null) {
				throw new IOException(MessageFormat
						.format(JGitText.get().encryptionError
			}
			if (vers == null) {
				throw new IOException(MessageFormat
						.format(JGitText.get().encryptionError
			}
			if (cont == null) {
				throw new IOException(MessageFormat
						.format(JGitText.get().encryptionError
			}
			if (!profile.equals(prof)) {
				throw new IOException(MessageFormat.format(
						JGitText.get().unsupportedEncryptionAlgorithm
			}
			if (!version.equals(vers)) {
				throw new IOException(MessageFormat.format(
						JGitText.get().unsupportedEncryptionVersion
			}
			try {
				decryptCipher = Cipher.getInstance(cipherAlgo);
				if (cont.isEmpty()) {
					decryptCipher.init(Cipher.DECRYPT_MODE
				} else {
					AlgorithmParameters params = AlgorithmParameters
							.getInstance(paramsAlgo);
					params.init(Base64.decode(cont));
					decryptCipher.init(Cipher.DECRYPT_MODE
				}
			} catch (Exception e) {
				throw error(e);
			}
		}

		@Override
		InputStream decrypt(InputStream input) throws IOException {
			try {
				return new CipherInputStream(input
			} finally {
			}
		}
	}

	static class JGitV1 extends SymmetricEncryption {


		static Properties wrap(String algo
			Properties props = new Properties();
			props.put(AmazonS3.Keys.CRYPTO_ALG
			props.put(AmazonS3.Keys.CRYPTO_VER
			props.put(AmazonS3.Keys.PASSWORD
			props.put(algo + Keys.X_ALGO
			props.put(algo + Keys.X_KEY_ALGO
			props.put(algo + Keys.X_KEY_ITER
			props.put(algo + Keys.X_KEY_SIZE
			props.put(algo + Keys.X_KEY_SALT
			return props;
		}

		JGitV1(String algo
				throws GeneralSecurityException {
			super(wrap(algo
			String name = cipherAlgo.toUpperCase();
			Matcher matcherPBE = Pattern.compile(REGEX_PBE).matcher(name);
			if (!matcherPBE.matches())
				throw new GeneralSecurityException(
						JGitText.get().encryptionOnlyPBE);
		}

	}

	static class JGitV2 extends SymmetricEncryption {


		JGitV2(Properties props)
				throws GeneralSecurityException {
			super(props);
		}
	}

	static WalkEncryption instance(Properties props)
			throws GeneralSecurityException {

		String algo = props.getProperty(AmazonS3.Keys.CRYPTO_ALG
		String vers = props.getProperty(AmazonS3.Keys.CRYPTO_VER
		String pass = props.getProperty(AmazonS3.Keys.PASSWORD);

			return WalkEncryption.NONE;

		switch (vers) {
		case Vals.DEFAULT_VERS:
			return new JetS3tV2(algo
		case JGitV1.VERSION:
			return new JGitV1(algo
		case JGitV2.VERSION:
			return new JGitV2(props);
		default:
			throw new GeneralSecurityException(MessageFormat.format(
					JGitText.get().unsupportedEncryptionVersion
		}
	}
