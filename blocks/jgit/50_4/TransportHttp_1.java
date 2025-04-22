	class SmartHttpFetchConnection extends BasePackFetchConnection {
		private static final String REQ_TYPE = "application/x-git-upload-pack-input";

		private static final String RSP_TYPE = "application/x-git-upload-pack-data";

		private final Set<RevObject> commonObjects = new HashSet<RevObject>();

		private final ByteArrayOutputStream bufOut;

		private byte[] bufferedWants = {};

		SmartHttpFetchConnection(final InputStream advertisement)
				throws TransportException {
			super(TransportHttp.this);

			init(advertisement
			outNeedsEnd = false;
			try {
				readAdvertisedRefs();
			} catch (IOException err) {
				close();
				throw new TransportException(uri
						"remote hung up unexpectedly"
			}

			in = new HttpInputStream(RSP_TYPE) {
				@Override
				HttpURLConnection call() throws IOException {
					return completeCall();
				}
			};
			pckIn = new PacketLineIn(in);

			bufOut = new ByteArrayOutputStream();
			out = new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					startCall();
					bufOut.write(b);
				}

				@Override
				public void write(byte[] b
						throws IOException {
					startCall();
					bufOut.write(b
				}
			};
			pckOut = new PacketLineOut(out);
			outNeedsEnd = true;
		}

		@Override
		boolean isBiDirectionalPipe() {
			return false;
		}

		void startCall() throws IOException {
			if (bufOut.size() == 0) {
				in.close();
				bufOut.write(bufferedWants);
				for (RevObject c : commonObjects)
					pckOut.writeString("have " + c.getId().name() + "\n");
			}
		}

		HttpURLConnection completeCall() throws IOException {
			final URL url = new URL(baseUrl
			Proxy proxy = HttpSupport.proxyFor(proxySelector
			HttpURLConnection conn;

			final byte[] body = bufOut.toByteArray();
			bufOut.reset();

			conn = (HttpURLConnection) url.openConnection(proxy);
			conn.setRequestMethod(HttpSupport.METHOD_POST);
			conn.setInstanceFollowRedirects(false);
			conn.setDoOutput(true);
			conn.setFixedLengthStreamingMode(body.length);
			conn.setRequestProperty(HttpSupport.HDR_CONTENT_TYPE
			final OutputStream os = conn.getOutputStream();
			try {
				os.write(body);
			} finally {
				os.close();
			}
			return conn;
		}

		@Override
		boolean sendWants(Collection<Ref> want) throws IOException {
			final boolean have = super.sendWants(want);
			bufferedWants = bufOut.toByteArray();
			bufOut.reset();
			return have;
		}

		@Override
		void markCommon(final RevObject obj) {
			super.markCommon(obj);
			commonObjects.add(obj);
		}
	}

