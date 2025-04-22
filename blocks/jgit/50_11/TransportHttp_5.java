	public FetchConnection openFetch() throws TransportException
			NotSupportedException {
		final String service = SVC_UPLOAD_PACK;
		try {
			final HttpURLConnection c = connect(service);
			final InputStream in = openInputStream(c);
			try {
				if (isSmartHttp(c
					readSmartHeaders(in
					return new SmartHttpFetchConnection(in);

				} else {
					HttpObjectDB d = new HttpObjectDB(objectsUrl);
					WalkFetchConnection wfc = new WalkFetchConnection(this
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in
					try {
						wfc.available(d.readAdvertisedImpl(br));
					} finally {
						br.close();
					}
					return wfc;
				}
			} finally {
				in.close();
			}
		} catch (NotSupportedException err) {
			throw err;
		} catch (TransportException err) {
			throw err;
		} catch (IOException err) {
			throw new TransportException(uri
		}
