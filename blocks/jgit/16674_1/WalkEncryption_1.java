	static class AesEncryption extends WalkEncryption {
		SecretKey secret;


		public AesEncryption(final String key
			byte[] salt = { (byte) 0xA4
					(byte) 0xD6

			try {
				SecretKeyFactory factory = SecretKeyFactory
				KeySpec spec = new PBEKeySpec(key.toCharArray()
						keyLen);
				SecretKey tmp = factory.generateSecret(spec);
				secret = new SecretKeySpec(tmp.getEncoded()
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}

		}

		@Override
		OutputStream encrypt(OutputStream os) throws IOException {
			Cipher cipher;
			try {
				cipher = Cipher.getInstance(algorithmName);
				cipher.init(Cipher.ENCRYPT_MODE

				AlgorithmParameters params = cipher.getParameters();
				byte[] iv = params.getParameterSpec(IvParameterSpec.class)
						.getIV();

				os.write(iv.length);
				os.write(iv);

				return new CipherOutputStream(os
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidParameterSpecException e) {
				e.printStackTrace();
			}
			return null;

		}

		@Override
		InputStream decrypt(InputStream in) throws IOException {
			Cipher cipher;
			try {
				cipher = Cipher.getInstance(algorithmName);
				if (in.available() > 0) {


					int ivlen = in.read();
					byte[] iv = new byte[ivlen];
					in.skip(3);
					in.read(iv
					in.skip(3);
					cipher.init(Cipher.DECRYPT_MODE
							new IvParameterSpec(iv));

					return new CipherInputStream(in
				}

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		void request(HttpURLConnection u
			u.setRequestProperty(prefix + JETS3T_CRYPTO_VER
			u.setRequestProperty(prefix + JETS3T_CRYPTO_ALG
 algorithmName);
		}

		@Override
		void validate(HttpURLConnection u
			validateImpl(u
		}

	}

