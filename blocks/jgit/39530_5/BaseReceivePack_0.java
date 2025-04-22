	interface NonceGenerator {
		public String createNonce(String seed
			throws IllegalStateException;
	}

	class DefaultNonceGenerator implements NonceGenerator {
		public String createNonce(String seed
			throws IllegalStateException
		{

			byte[] keyBytes = key.getBytes();
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes

			Mac mac = null;
			try {
				mac.init(signingKey);
			} catch (InvalidKeyException e) {
				throw new IllegalStateException(e);
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalStateException(e);
			}
			byte[] rawHmac = mac.doFinal(seed.getBytes());
			String hexString = String.format("%20X"
			return hexString;
		}
	}

	protected class PushCertificateParser {

		private String seed;

		private String sentNonce;

		private String receivedNonce;

		private int nonceSlopLimit;

		private String nonceStatus;

		private String pusher;

		private String pushee;

		NonceGenerator nonceGenerator;

		PushCertificateParser(ReceiveConfig cfg
			seed = cfg.certNonceSeed;
			if (seed != null)
				sentNonce = nonceGenerator.createNonce(seed
						TimeUnit.MILLISECONDS.toSeconds(
								System.currentTimeMillis()));

			nonceSlopLimit = cfg.certNonceSlopLimit;
			this.dirPath = dirPath;
			nonceGenerator = new DefaultNonceGenerator();
		}

		public String getNonceStatus() {
			return nonceStatus;
		}

		public String getPusher() {
			return pusher;
		}

		public String getPushee() {
			return pushee;
		}

		protected String dirPath;

		protected String commandlist;
		protected StringBuilder commandlistBuilder

		protected String signature;

		protected String checkNonce() {
			if (receivedNonce.isEmpty())
				return NONCE_MISSING;
			else if (sentNonce.isEmpty())
				return NONCE_UNSOLICITED;
			else if (receivedNonce.equals(sentNonce))
				return NONCE_OK;


			int idxSent = sentNonce.indexOf('-');
			int idxRecv = receivedNonce.indexOf('-');
			if (idxSent == -1 || idxRecv == -1)
				return NONCE_BAD;

			long signedStamp = 0;
			long advertisedStamp = 0;
			try {
				signedStamp = Integer.parseInt(
						receivedNonce.substring(0
				advertisedStamp = Integer.parseInt(
						sentNonce.substring(0
			} catch (Exception e) {
				return NONCE_BAD;
			}

			String expect = nonceGenerator.createNonce(
					seed

			if (!expect.equals(receivedNonce))
				return NONCE_BAD;

			long nonceStampSlop = Math.abs(advertisedStamp - signedStamp);

			if (nonceSlopLimit != 0
					&& nonceStampSlop <= nonceSlopLimit) {
				return NONCE_OK;
			} else {
				return NONCE_SLOP;
			}
		}

		public boolean enabled() {
			return seed != null;
		}

		public String getAdvertiseNonce() {
					nonceGenerator.createNonce(seed
							TimeUnit.MILLISECONDS.toSeconds(
									System.currentTimeMillis()));
		}

		protected void receiveHeader() throws IOException {
			try {
				String version = versionPattern.matcher(pckIn.readStringRaw()).group(1);
					pusher = pusherPattern.matcher(pckIn.readStringRaw()).group(1);
					pushee = pusheePattern.matcher(pckIn.readStringRaw()).group(1);
					receivedNonce = noncePattern.matcher(pckIn.readStringRaw()).group(1);
				} else {
					throw new IOException(MessageFormat.format(
							JGitText.get().errorInvalidPushCert
				}
			} catch (EOFException eof) {
				throw new IOException(MessageFormat.format(
							JGitText.get().errorInvalidPushCert
			}
			checkNonce();
		}

		protected boolean verifySignedPushCommands(String commandList
				String signature) {
			return false;
		}

		protected void receiveSignature() throws IOException {
			try {
				StringBuilder sig = new StringBuilder();
				String line = pckIn.readStringRaw();
					sig.append(line);
				signature = sig.toString();
				verifySignedPushCommands(commandlist
			} catch (EOFException eof) {
				throw new IOException(MessageFormat.format(
						JGitText.get().errorInvalidPushCert
			}
		}
	}

	PushCertificateParser pushCertificateParser;

