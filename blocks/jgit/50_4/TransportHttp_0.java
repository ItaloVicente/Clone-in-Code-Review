		try {
			final String service = "git-upload-pack";
			final HttpURLConnection c = connect(service);
			final InputStream in = new BufferedInputStream(c.getInputStream());
			try {
				String expType = "application/x-" + service + "-advertisement";
				String actType = c.getContentType();
				if (expType.equals(actType)) {
					checkPacketLineStream(in
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
		} catch (IOException err) {
			throw new TransportException(uri
		}
