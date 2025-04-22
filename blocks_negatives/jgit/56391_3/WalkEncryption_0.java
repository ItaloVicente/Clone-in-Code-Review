				final Cipher c = Cipher.getInstance(algorithmName);
				c.init(Cipher.ENCRYPT_MODE, skey, aspec);
				return new CipherOutputStream(os, c);
			} catch (NoSuchAlgorithmException e) {
				throw error(e);
			} catch (NoSuchPaddingException e) {
				throw error(e);
			} catch (InvalidKeyException e) {
				throw error(e);
			} catch (InvalidAlgorithmParameterException e) {
