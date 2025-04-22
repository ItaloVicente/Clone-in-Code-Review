	private FetchConnection newDumbConnection(InputStream in)
			throws IOException
		HttpObjectDB d = new HttpObjectDB(objectsUrl);
		BufferedReader br = toBufferedReader(in);
		Map<String
		try {
			refs = d.readAdvertisedImpl(br);
		} finally {
			br.close();
		}

		if (!refs.containsKey(Constants.HEAD)) {
			HttpURLConnection conn = httpOpen(new URL(baseUrl
			int status = HttpSupport.response(conn);
			switch (status) {
			case HttpURLConnection.HTTP_OK: {
				br = toBufferedReader(conn.getInputStream());
				try {
					String line = br.readLine();
					if (line != null && line.startsWith("ref: ")) {
						Ref src = refs.get(line.substring(5));
						if (src != null) {
							refs.put(Constants.HEAD
									Ref.Storage.NETWORK
											.getName()
						}
					} else if (line != null && ObjectId.isId(line)) {
						refs.put(Constants.HEAD
								Constants.HEAD
					}
				} finally {
					br.close();
				}
				break;
			}

			case HttpURLConnection.HTTP_NOT_FOUND:
				break;

			default:
				throw new TransportException(uri
						+ " " + conn.getResponseMessage());
			}
		}

		WalkFetchConnection wfc = new WalkFetchConnection(this
		wfc.available(refs);
		return wfc;
	}

	private BufferedReader toBufferedReader(InputStream in) {
		return new BufferedReader(new InputStreamReader(in
	}

