				final Cipher c = Cipher.getInstance(algorithmName);
				c.init(Cipher.DECRYPT_MODE, skey, aspec);
				return new CipherInputStream(in, c);
			} catch (NoSuchAlgorithmException e) {
				throw error(e);
			} catch (NoSuchPaddingException e) {
				throw error(e);
			} catch (InvalidKeyException e) {
				throw error(e);
			} catch (InvalidAlgorithmParameterException e) {
